package com.schindig.controllers;
import com.fasterxml.jackson.annotation.JsonView;
import com.schindig.entities.*;
import com.schindig.services.*;
import com.schindig.utils.Methods;
import com.schindig.utils.Parameters;
import com.schindig.utils.PasswordHash;
import com.schindig.utils.Venmo;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Console;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Agronis on 12/9/15.
 */

@RestController
public class MainController {


    @Autowired
    WizardRepo wizard;

    @Autowired
    FavorRepo favors;

    @Autowired
    PartyRepo parties;

    @Autowired
    UserRepo users;

    @Autowired
    FavorListRepo favlists;

    @Autowired
    InviteRepo invites;

    @Autowired
    AuthRepo auth;

    @Autowired
    ContactRepo contacts;


    @PostConstruct
    public void init() throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidKeyException, InvalidKeySpecException {
            Methods.script(users, favors, parties, favlists, invites, wizard, auth);

    }


    @RequestMapping(path = "/validate/{device}", method = RequestMethod.GET)
    public Integer appLoad(@PathVariable("device") String device, HttpServletResponse response) throws IOException {
        try {
        Auth a = auth.findByDevice(device);
        if (a == null) {
            response.sendError(400, "You must log in to continue.");
            return 0;
        } else {
            Auth a2 = auth.findByDevice(device);
            User user = a2.user;
//            response.sendError(200, "Welcome back ".concat(user.username).concat("!"));
            return user.userID;
        }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendError(400, "There was an error authorizing this device.");
        }
        return null;
    }

    /**
     * ALL USER RELATED ROUTES
     **/
    @RequestMapping(path = "/user/update", method = RequestMethod.POST)
    public User updateUser(@RequestBody User u, HttpServletResponse response) throws IOException {
        try {
        User user = users.findOne(u.userID);
        if (u.username != null) {
            user.username = u.username.toLowerCase();
        }
        if (u.password != null) {
            user.password = u.password.toLowerCase();
        }
        if (u.email != null) {
            user.email = u.email.toLowerCase();
        }
        if (u.phone != null) {
            user.phone = u.phone;
        }
        if (u.firstName != null) {
            user.firstName = u.firstName;
        }
        if (u.lastName != null) {
            user.lastName = u.lastName;
        }
        users.save(user);
        user.password = null;
        response.sendError(200, "Your profile has been updated!");
        return user;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendError(400, "There was an error updating this user.");
        }
        return null;
    }

    @RequestMapping(path = "/user/create", method = RequestMethod.POST)
    public void createUser(@RequestBody User user, HttpServletResponse response, HttpSession session) throws Exception {
        try {
        User u = users.findOneByUsername(user.username.toLowerCase());
        if (u != null) {
            response.sendError(400, "Username already exists.");
        } else if (user.username.length() < 5) {
            response.sendError(400, "Username must be at least five characters.");
        } else if (user.password.length() <= 5) {
            response.sendError(400, "Password must be greater then five characters.");
        } else if (!Methods.charCheck(user.password)) {
            response.sendError(400, "Password may only contain letters or numbers.");
        } else if (!Methods.charCheck(user.username)) {
            response.sendError(400, "Username may only contain letters or numbers.");
        } else if (!Methods.isValidEmailAddress(user.email)) {
            response.sendError(400, "Please enter a valid email address.");
        } else if (!Methods.phoneCheck(user.phone)) {
            response.sendError(400, "Please enter a phone number containing only digits.");
        } else if (user.phone.length() != 10) {
            response.sendError(400, "Phone number must be ten digits in length.");
        } else if (users.findByPhone(user.phone) != null) {
            response.sendError(400, "Phone number is already associated to an account.");
        } else if (users.findByEmail(user.email) != null) {
            response.sendError(400, "Email is already associated to an account.");
        }

        response.sendError(200, "Account successfully created.");
        User newUser = new User();
        newUser.username = user.username.toLowerCase();
        newUser.phone = user.phone;
        newUser.password = user.password.toLowerCase();
        newUser.email = user.email.toLowerCase();
        newUser.firstName = user.firstName;
        newUser.lastName = user.lastName;
        users.save(newUser);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendError(400, "There was an error creating a user.");
        }
    }

    @RequestMapping(path = "/user/delete", method = RequestMethod.POST)
    public void deleteUser(@RequestBody User user, HttpServletResponse response) throws IOException {
        try {
        if (user.username.equals("admin")) {
            users.delete(user);
        } else {
            response.sendError(400, "Not authorized");
        }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendError(400, "There was an error when deleting a user.");
        }

    }

    @RequestMapping(path = "/user/all", method = RequestMethod.GET)
    public ArrayList<User> getAllUsers(HttpServletResponse response) throws IOException {
        try {
        ArrayList<User> temp = (ArrayList<User>) users.findAll();
        temp = temp.stream()
                .map(p -> {
                    p.password = null;
                    return p;
                })
                .collect(Collectors.toCollection(ArrayList<User>::new));
        return temp;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendError(400, "There was an error requesting all the users.");
        }
        return null;
    }

    @RequestMapping(path = "/user/{id}", method = RequestMethod.GET)
    public User findOneUser(@PathVariable("id") int id, HttpServletResponse response) throws IOException {
        try {
        User u = users.findOne(id);
        u.password = null;
        return u;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendError(400, "There was an error retreiving a user.");
        }
        return null;
    }

    @RequestMapping(path = "/user/login", method = RequestMethod.POST)
    public Integer login(@RequestBody Parameters p, HttpServletResponse response, HttpSession session, HttpServletRequest request) throws Exception {
        try {
        User user = users.findOneByUsername(p.user.username.toLowerCase());
        if (user == null) {
            response.sendError(401, "Username not found.");
        } else if (!p.user.password.toLowerCase().equals(user.password)) {
            response.sendError(403, "Credentials do not match our records.");
        } else {
            Auth a = auth.findByDevice(p.device);
            if (a == null) {
                Methods.newDevice(user, p.device, auth);
                return user.userID;
            }
        }
        return user.userID;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @RequestMapping(path = "/user/logout", method = RequestMethod.POST)
    public void logout(@RequestBody Parameters p, HttpServletResponse response) throws IOException {
        try {
            Auth a = auth.findByDevice(p.device);
            auth.delete(a);
            response.sendError(200, "You've successfully been logged out.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendError(400, "You can't logout!");
        }

    }


    @RequestMapping(path = "/{partyID}/{userID}/contacts", method = RequestMethod.GET)
    public ArrayList<Contact> getContacts(@PathVariable("userID") Integer userID, @PathVariable("partyID") Integer partyID) {
        try {
            Party p = parties.findOne(partyID);
            User u = users.findOne(userID);
            ArrayList<Contact> returnList = new ArrayList<>();
            ArrayList<Contact> contactList = contacts.findAllByUser(u);
            contactList = contactList.stream()
                    .sorted(Comparator.comparing(Contact::getName))
                    .collect(Collectors.toCollection(ArrayList::new));
            ArrayList<Invite> inviteList = invites.findByParty(p);
            for (Contact contact : contactList) {
                for (Invite invite : inviteList) {
                    if (invite.phone.equals(contact.phone)) {

                        returnList.add(contact);
                    }
                }
            }
            if (returnList.size() == 0) {
                if (contactList != null) {
                    return contactList;
                } else {
                    return null;
                }
            } else {
                return returnList;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * ALL PARTY RELATED ROUTES
     **/

    @RequestMapping(path = "/party/create", method = RequestMethod.POST)
    public Party createParty(@RequestBody Parameters params, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {

        try {
            User user = users.findOne(params.userID);
            Party party = params.party;
            party.host = user;
            user.hostCount += 1;
            users.save(user);
            parties.save(party);
            Invite i = new Invite();
            i.party = party;
            i.rsvpStatus = "Yes";
            i.name = user.firstName.concat(" ").concat(user.lastName);
            i.email = user.email;
            i.phone = user.phone;
            i.user = user;
            invites.save(i);
            return party;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @RequestMapping(path = "/party/claim", method = RequestMethod.POST)
    public FavorList claimFavor(@RequestBody Parameters p, HttpServletResponse response) throws IOException {
        try {
            User u = users.findOne(p.userID);
            FavorList favItem = favlists.findOne(p.listID);
            if (favItem.user != u && favItem.user != null) {
                response.sendError(403, "Not your's to unclaim.");
            } else {
                favItem.user = u;
                if (favItem.claimed) {
                    favItem.claimed = false;
                    response.sendError(200, "You're no longer bringing " + favItem.favor.favorName + " to " + favItem.party.partyName + "!");
                } else {
                    favItem.claimed = true;
                    response.sendError(200, "You're now bringing " + favItem.favor.favorName + " to " + favItem.party.partyName + "!");
                }
                favlists.save(favItem);
            }
            return favItem;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @RequestMapping(path = "/party/{id}/favors", method = RequestMethod.GET)
    public ArrayList<FavorList> getFavors(@PathVariable("id") int id) {
        try {
            ArrayList<FavorList> favorList = (ArrayList<FavorList>) favlists.findAll();
            ArrayList<FavorList> newList = favorList.stream().filter(fav -> fav.party.partyID == id).collect(Collectors.toCollection(ArrayList::new));
            if (newList == null) {
                return null;
            }
            return newList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @RequestMapping(path = "/party/{id}/filter", method = RequestMethod.GET)
    public ArrayList<Favor> getUnusedFavors(@PathVariable("id") Integer id) {
        try {
            Party party = parties.findOne(id);
            ArrayList<FavorList> list = favlists.findAllByParty(party);
            ArrayList<Favor> check = (ArrayList<Favor>) favors.findAll();
            ArrayList<Favor> inParty = list.stream()
                    .map(fav -> fav.favor)
                    .collect(Collectors.toCollection(ArrayList::new));
            check = check.stream()
                    .filter(fav -> !inParty.contains(fav))
                    .filter(f -> /**f.partyType.equals("Generic") || **/f.partyType.equals(party.partyType))
                    .sorted(Comparator.comparing(Favor::getUseCount).reversed())
                    .collect(Collectors.toCollection(ArrayList<Favor>::new));
            return check;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @RequestMapping(path = "/party/invite", method = RequestMethod.POST)
    public void addInvite(@RequestBody Parameters parameters, HttpServletResponse response) throws Exception {
        try {
            Party party = parties.findOne(parameters.party.partyID);
            User user = users.findOne(parameters.user.userID);
            User host = party.host;
            host.inviteCount += 1;
            Invite invite = new Invite(
                    user, party, parameters.invites.phone, parameters.invites.email, "RSVP", parameters.invites.name);
            response.sendError(200, invite.name + " is now invited to " + invite.party.partyName + "!");
            users.save(host);
            invites.save(invite);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @RequestMapping(path = "/party/rsvp", method = RequestMethod.POST)
    public void rsvp(@RequestBody Parameters p, HttpServletResponse response) throws IOException {
        try {
            Party party = parties.findOne(p.partyID);
            User user = users.findOne(p.userID);
            user.invitedCount += 1;
            ArrayList<Invite> list = invites.findByParty(party);
            Invite i = new Invite();
            for (Invite inv : list) {
                if (inv.phone.equals(user.phone) || inv.email.equals(user.email)) {
                    i = inv;
                    break;
                }
            }
            i.user = user;
            i.rsvpStatus = p.invites.rsvpStatus;
            response.sendError(200, "Thanks for RSVPing to " + party.partyName);
            invites.save(i);
            users.save(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @RequestMapping(path = "/party/{partyID}/{userID}", method = RequestMethod.GET)
    public ArrayList<Object> getParty(@PathVariable("partyID") Integer partyID, @PathVariable("userID") Integer userID) {
        try {
            Party party = parties.findOne(partyID);
            User user = users.findOne(userID);
            ArrayList<Invite> inviteList = invites.findByParty(party);
            inviteList = inviteList.stream()
                    .filter(Methods.distinctByKey(Invite::getName))
                    .collect(Collectors.toCollection(ArrayList::new));
            if (party.host != user) {
                Invite rsvp = invites.findByPartyAndPhone(party, user.phone);
                if (rsvp.rsvpStatus==null) {
                    party.rsvpStatus.equals("RSVP");
                }
                party.rsvpStatus = rsvp.rsvpStatus;
            } else {
                party.rsvpStatus = "You're the host!";
            }
            parties.save(party);
            ArrayList<Object> payload = new ArrayList<>();
            HashMap<String, Object> inviteDump = new HashMap<>();
            HashMap<String, Object> userList = new HashMap<>();
            userList.put("host", user.userID);
            userList.put("firstName", user.firstName);
            userList.put("lastName", user.lastName);
            userList.put("venmoID", user.getVenmoID());
            payload.add(userList);

            for (Invite invite : inviteList) {
                HashMap<String, Object> newMap = new HashMap<>();
                newMap.put("inviteID", invite.inviteID);
                newMap.put("guest", invite.user);
                newMap.put("phone", invite.phone);
                newMap.put("email", invite.email);
                newMap.put("name", invite.name);
                inviteDump.put(String.valueOf(invite.inviteID), newMap);
            }
            payload.add(party);
            payload.add(inviteDump);
            return payload;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @RequestMapping(path = "/party/{id}/invites", method = RequestMethod.GET)
    public ArrayList<Invite> getInvites(@PathVariable("id") Integer id) {
        try {
            Party p = parties.findOne(id);
            return invites.findByParty(p);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @RequestMapping(path = "/party/update", method = RequestMethod.PATCH)
    public Party updateParty(@RequestBody Parameters parameters, HttpServletResponse response) throws MessagingException, IOException {
      try {
          Party check = parties.findOne(parameters.party.partyID);
          if (parameters != null) {

              if (parameters.party.description != null) {
                  check.description = parameters.party.description;
              }
              if (parameters.party.partyName != null) {
                  check.partyName = parameters.party.partyName;
              }
              if (parameters.party.partyDate != null) {
                  check.partyDate = parameters.party.partyDate;
              }
              if (parameters.party.partyType != null) {
                  check.partyType = parameters.party.partyType;
              }
              if (parameters.party.subType != null) {
                  check.subType = parameters.party.subType;
              }
              if (parameters.party.local != null) {
                  check.local = parameters.party.local;
              }
              if (parameters.party.stretchGoal != null) {
                  check.stretchGoal = parameters.party.stretchGoal;
              }
              if (parameters.party.stretchName != null) {
                  check.stretchName = parameters.party.stretchName;
              }
              if (parameters.party.stretchStatus != null && parameters.party.stretchStatus != 0.0) {
                  if ((check.stretchStatus += parameters.party.stretchStatus) > check.stretchGoal) {
                      double diff = (check.stretchStatus += parameters.party.stretchStatus) - check.stretchGoal;
                      check.stretchStatus += (parameters.party.stretchStatus - diff);
                      response.sendError(200, check.stretchName + "'s goal has been fulfilled!");
                  } else {
                      check.stretchStatus = parameters.party.stretchStatus;
                  }
              }
              if (parameters.party.theme != null) {
                  check.theme = parameters.party.theme;
              }
              if (parameters.inviteDump != null) {
                  for (Invite invite : parameters.inviteDump) {
                      response.sendError(200, "Invites sent!");
                      Methods.newInvite(invite, invites, check);
                      Methods.msgGateway(invite, check.host, check);
                      invite.sent = true;
                      check.host.inviteCount += 1;
                  }
              }
              if (parameters.party.byob) {
                  check.byob = true;
              }
              if (parameters.party.parking != null) {
                  check.parking = parameters.party.parking;
              }
              if (parameters.party.wizPosition != null) {
                  check.wizPosition = parameters.party.wizPosition;
              }
              parties.save(check);
              return check;
          }
          if (parameters != null) {
              response.sendError(200, "Updated " + check.partyName + "'s details.");
          } else {
              response.sendError(200, "No updates added.");
          }
      } catch (Exception e) {
          System.out.println(e.getMessage());
      }
        return null;
    }

    @RequestMapping(path = "/parties/host", method = RequestMethod.POST)
    public ArrayList<Party> getAllHosted(@RequestBody User user) {
        try {
            User u = users.findOne(user.userID);
            ArrayList<Party> partyList = (ArrayList<Party>) parties.findAll();
            partyList = (ArrayList<Party>) partyList.stream()
                    .filter(p -> p.host == u)
                    .collect(Collectors.toCollection(ArrayList<Party>::new));
            return partyList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @RequestMapping(path = "/parties/user", method = RequestMethod.POST)
    public ArrayList<Party> getAllParties(@RequestBody User user) {
        try {
            User u = users.findOne(user.userID);
            ArrayList<Invite> inviteList = (ArrayList<Invite>) invites.findAll();
            ArrayList<Party> partyList = new ArrayList();
            for (Invite invite : inviteList) {
                if (u.email.equals(invite.email)) {
                    partyList.add(invite.party);
                } else if (u.phone.equals(invite.phone)) {
                    partyList.add(invite.party);
                }
            }
            return partyList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @RequestMapping(path = "/party/delete", method = RequestMethod.POST)
    public void deleteParty(@RequestBody Party party, HttpServletResponse response) throws IOException {
        try {
            Party p = parties.findOne(party.partyID);
            User u = p.host;
            ArrayList<FavorList> f = favlists.findAllByParty(p);
            ArrayList<Invite> i = invites.findByParty(party);

            if (f != null) {
                for (FavorList stuff : f) {
                    favlists.delete(stuff);
                }
            }

            if (i != null) {
                for (Invite list : i) {
                    invites.delete(list);
                }
            }

            u.hostCount -= 1;
            response.sendError(200, p.partyName + " has been cancelled.");
            users.save(u);
            parties.delete(p);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @RequestMapping(path = "/party/favor/delete", method = RequestMethod.POST)
    public FavorList deletePartyFavor(@RequestBody Parameters parameters, HttpServletResponse response) throws IOException {
        try {
            FavorList f = favlists.findOne(parameters.listID);
            Favor fav = f.favor;
            fav.useCount -= 1;
            response.sendError(200, fav.favorName + " has been removed from this party.");
            favors.save(fav);
            favlists.delete(f);
            return f;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @RequestMapping(path = "/wizard", method = RequestMethod.GET)
    public ArrayList<Wizard> getPartyList() {
        try {
            ArrayList<Wizard> wiz = (ArrayList<Wizard>) wizard.findAll();
            wiz = wiz.stream()
                    .sorted(Comparator.comparing(Wizard::getPartyType))
                    .collect(Collectors.toCollection(ArrayList::new));
            return wiz;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @RequestMapping(path = "/wizard/{id}", method = RequestMethod.POST)
    public Party wizardPosition(@RequestBody Party p, @PathVariable("id") Integer id) {
        try {
            Party party = parties.findOne(p.partyID);
            party.wizPosition = id + 1;
            parties.save(party);
            return party;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @RequestMapping(path = "/wizard/pos", method = RequestMethod.GET)
    public Integer getWizardPosition(@RequestBody Party party) {
        try {
            return parties.findOne(party.partyID).wizPosition;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    @RequestMapping(path = "/favor/{id}", method = RequestMethod.GET)
    public ArrayList<Favor> getFavorList(@PathVariable("id") Integer id) {
        try {
            Party party = parties.findOne(id);
            ArrayList<Favor> all = (ArrayList<Favor>) favors.findAll();
            all = all.stream()
                    .filter(f ->/** f.partyType.equals("Generic") || **/f.partyType.equals(party.partyType))
                    .sorted(Comparator.comparing(Favor::getUseCount).reversed())
                    .collect(Collectors.toCollection(ArrayList<Favor>::new));
            return all;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @RequestMapping(path = "/party/favor", method = RequestMethod.POST)
    public ArrayList<Favor> addPartyFavor(@RequestBody Parameters parameters, HttpServletResponse response) throws IOException {
        try {
            ArrayList<Favor> newDump = new ArrayList<>();
            Party party = parties.findOne(parameters.partyID);
            for (int i = 0; i < parameters.favorDump.size(); i++) {
                if (parameters.favorDump.get(i).favorID != null) {
                    Favor fav = favors.findOne(parameters.favorDump.get(i).favorID);
                    fav.useCount += 1;
                    FavorList favorList = new FavorList(fav, party, false);
                    favlists.save(favorList);
                    favors.save(fav);
                    newDump.add(fav);
                } else {
                    Favor fav = new Favor();
                    fav.useCount += 1;
                    fav.favorName = parameters.favorDump.get(i).favorName;
                    fav.partyType = party.partyType;
                    FavorList favorList = new FavorList(fav, party, false);
                    favlists.save(favorList);
                    favors.save(fav);
                    newDump.add(fav);
                }
            }
            response.sendError(200, "Favors added to " + party.partyName + "!");
            newDump = newDump.stream()
                    .sorted(Comparator.comparing(Favor::getFavorName))
                    .collect(Collectors.toCollection(ArrayList::new));
            return newDump;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @RequestMapping(path = "/favor/save", method = RequestMethod.POST)
    public Favor addFavorItem(@RequestBody Parameters p, HttpServletResponse response) throws IOException {
        try {
            Favor fav = new Favor();
            Party party = parties.findOne(p.partyID);
            if (p.favor.favorName == null || p.favor.favorName.isEmpty()) {
                response.sendError(400, "Please give this party favor a name.");
                return null;
            } else {
                fav.favorName = p.favor.favorName;
                fav.partyType = party.partyType;
                fav.useCount += 1;
                favors.save(fav);
                return fav;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @RequestMapping(path = "/favor/remove", method = RequestMethod.POST)
    public ArrayList<Favor> deleteFavorItem(@RequestBody Favor item) {
        try {
            favors.delete(item);
            ArrayList<Favor> all = (ArrayList<Favor>) favors.findAll();
            all = all.stream()
                    .sorted(Comparator.comparing(Favor::getFavorName))
                    .collect(Collectors.toCollection(ArrayList::new));
            return all;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @RequestMapping(path = "/party/stats", method = RequestMethod.GET)
    public ArrayList<String> partyStats(@RequestBody Parameters p) {
        try {
            ArrayList<Object> stats = new ArrayList<>();
            long party = parties.count();
            long invite = invites.count();
            long wiz = wizard.count();
            long user = users.count();
            long favor = favors.count();

            ArrayList<Object> userStats;
            HashMap<String, Long> databaseStats;
            databaseStats = new HashMap<>();
            stats.add(databaseStats);

            return new ArrayList<>();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @RequestMapping(path = "/venmo", method = RequestMethod.GET)
    public void saveVenmo(String code, String state, HttpServletResponse response) throws IOException {
        try {
            if (code != null && state != null) {
                System.out.println("Venmo returned");
                HashMap<String, String> map = new HashMap<>();
                String[] relocate = state.split(":");
                User user = users.findOne(Integer.valueOf(relocate[1]));
                user.setVenmoCode(code);
                Methods.getVenmo(code, user, users);
                response.addHeader("Party", relocate[0]);
                response.sendRedirect("http://localhost:8100/#/invitedParty/" + relocate[0]);
            } else {
                response.sendRedirect("http://localhost:8100/#/");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @RequestMapping(path = "/venmo/payment", method = RequestMethod.POST)
    public void venmoPayment(@RequestBody Parameters p, HttpServletResponse response) throws IOException {
        try {
            System.out.println("Payment Taken");
            Party party = parties.findOne(p.partyID);
            User guest = users.findOne(p.userID);
            Double amount = Double.valueOf(p.amount);
            if (guest.getVenmoID() == null) {
                response.sendError(400, "No Venmo account found.");
            }
            if (!Objects.equals(Methods.sendPayment(guest, party, users, amount), "400")) {
                party.stretchStatus += amount;
                parties.save(party);
                response.sendRedirect("http://localhost:8100/#/invitedParty/" + party.partyID);
                return;
            } else {
                response.sendError(400, "There was an error processing your payment.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

//    public void addCorsHeader(HttpServletResponse response){
//        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8100/");
//        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
//        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
//        response.addHeader("Access-Control-Max-Age", "1728000");
//    }
//
//    @Singleton
//    public class CorsFilter implements Filter{
//
//        @Override
//        public void doFilter(ServletRequest request, ServletResponse response,
//                             FilterChain filterChain) throws IOException, ServletException {
//
//            if(response instanceof HttpServletResponse){
//                HttpServletResponse alteredResponse = ((HttpServletResponse)response);
//                addCorsHeader(alteredResponse);
//            }
//
//            filterChain.doFilter(request, response);
//        }
//
//        private void addCorsHeader(HttpServletResponse response){
//            response.addHeader("Access-Control-Allow-Origin", "http://localhost:8100/");
//            response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
//            response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
//            response.addHeader("Access-Control-Max-Age", "1728000");
//        }
//
//        @Override
//        public void destroy() {}
//
//        @Override
//        public void init(FilterConfig filterConfig)throws ServletException {}
//    }
}

/*
    @RequestMapping(path = "/user/search", method = RequestMethod.GET)
    public ArrayList<User> userSearch(@RequestBody User user) {
        ArrayList<User> allresults = (ArrayList<User>) users.findAll();
        ArrayList<User> results = allresults.stream()
                .filter(u -> u.username.equalsIgnoreCase(user.username) ||
                u.firstName.equalsIgnoreCase(user.firstName) ||
                u.lastName.equalsIgnoreCase(user.lastName) ||
                u.email.equalsIgnoreCase(user.email))
                .collect(Collectors.toCollection(ArrayList<User>::new));
        return results;
    }
    */
//    public void updateUserStats(User user) {
//        HashMap<String, String> stats = user.stats;
//            if (stats.get("partyCount")==null) {
//                stats.put("partyCount", String.valueOf(user.partyCount));
//            } else {
//                stats.replace("partyCount", String.valueOf(user.partyCount));
//            }
//            if (stats.get("hostCount") == null) {
//                stats.put("hostCount", String.valueOf(user.hostCount));
//            } else {
//                stats.replace("hostCount", String.valueOf(user.hostCount));
//            }
//            if (stats.get("inviteCount") == null) {
//                stats.put("inviteCount", String.valueOf(user.inviteCount));
//            } else {
//                stats.replace("inviteCount", String.valueOf(user.inviteCount));
//            }
//            if (stats.get("invitedCount") == null) {
//                stats.put("invitedCount", String.valueOf(user.invitedCount));
//            } else {
//                stats.replace("invitedCount", String.valueOf(user.invitedCount));
//            }
//
//        }
//    }
