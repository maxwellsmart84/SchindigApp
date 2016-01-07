package com.schindig.utils;
import com.schindig.AppConfig;
import com.schindig.controllers.MainController;
import com.schindig.entities.*;
import com.schindig.services.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.crypto.*;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * Created by Agronis on 12/9/15.
 */
public class Methods extends MainController {

    public static <T> Predicate<T> distinctByKey(Function<? super T,Object> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static void script(UserRepo users, FavorRepo favors, PartyRepo parties, FavorListRepo favlists, InviteRepo invites, WizardRepo wizard, AuthRepo auth) {
        long wizCheck = wizard.count();
        ArrayList<String> partyTypes = new ArrayList<>();
        ArrayList<String> subTypes = new ArrayList<>();
        if (wizCheck == 0) {
            String fileContent = Methods.readFile("wizard.csv");

            String[] lines = fileContent.split("\n");

            for (String line : lines) {
                Wizard wiz = new Wizard();
                String[] columns = line.split(",");
                String partyType = columns[0];
                String partyMod = columns[1];
                partyTypes.add(columns[0]);
                if (columns[1] != null) {
                    subTypes.add(columns[1]);
                }
                if (columns[1] == null) {
                    partyMod = "empty";
                }
                Wizard check = wizard.findOneByPartyType(partyType);
                if (check == null) {
                    Wizard test = new Wizard();
                    test.partyType = partyType;
                    ArrayList<String> subType = new ArrayList<>();
                    subType.add(partyMod);
                    test.subType = subType;
                    wizard.save(test);
                } else if (check.partyType.equals(partyType)) {
                    check.subType.add(partyMod);
                    wizard.save(check);
                } else {
                    wiz.partyType = partyType;
                    wiz.subType.add(partyMod);
                    wizard.save(wiz);
                }
            }
        }

        long catCheck = favors.count();
        if (catCheck == 0) {
            String fileContent = Methods.readFile("catalog.csv");

            String[] lines = fileContent.split("\n");


            for (String line : lines) {
                Favor fav = new Favor();
                String[] columns = line.split(",");
                fav.favorName = columns[0];
                fav.partyType = columns[1];
                favors.save(fav);
            }
        }

        ArrayList<User> userBuild = (ArrayList<User>) users.findAll();
        if (userBuild.size() < 10) {

            User admin = new User("admin", "pass", "The", "Admin", "schindig.app@gmail.com", "1234567890");
            users.save(admin);

            String fileContent = Methods.readFile("users.csv");

            String[] lines = fileContent.split("\n");
            for (String line : lines) {
                String randomNumber = RandomStringUtils.randomNumeric(10);
                String[] columns = line.split(",");
                User u = new User(columns[0], columns[1], columns[2], columns[3], columns[2].concat(columns[4]), randomNumber);
                userBuild.add(u);
                users.save(u);
            }
            String description = "Three long months, sleepless nights and lots of ping pong have led us to this point.";
            String theme = "Recognize our hard work!";
            String local = "17 Princess St, Charleston SC 29464";
            String stretchName = "Earth, Wind & Fire";

            User blake = new User();
            blake.username = "blake182";
            blake.password = "pass";
            blake.firstName = "Blake";
            blake.lastName = "Guillo";
            blake.email = "erlewis288@gmail.com";
            blake.phone = "8034644711";

            User joshua = new User();
            joshua.username = "agro";
            joshua.password = "pass";
            joshua.firstName = "Joshua";
            joshua.lastName = "Roberson";
            joshua.email = "agronis@icloud.com";
            joshua.phone = "8439019708";
            users.save(blake);
            users.save(joshua);

            Favor pong = new Favor();
            pong.favorName = "Ping Pong Balls";
            pong.partyType = "Graduation";
            pong.useCount = 100;
            favors.save(pong);
            Favor a = new Favor();
            a.partyType = "Graduation";
            a.favorName = "Alcohol";
            a.useCount = 99;
            favors.save(a);
            Favor b = new Favor();
            b.favorName = "Cards Against Humanity";
            b.partyType = "Graduation";
            b.useCount = 98;
            Favor c = new Favor();
            c.favorName = "More Alcohol";
            c.partyType = "Graduation";
            c.useCount = 97;
            favors.save(c);
            Favor d = new Favor();
            d.favorName = "Video Games";
            d.partyType = "Graduation";
            d.useCount = 96;
            favors.save(d);

            Party grad = new Party(blake, "The Iron Party", "Graduation", description, null,
                    LocalDateTime.now(), String.valueOf(LocalDateTime.now().plusDays(2)), local, stretchName, 3000,
                    0.0, true, true, theme, "Valet");
            parties.save(grad);
            Invite x = new Invite();
            x.party = grad;
            x.user = blake;
            x.name = blake.firstName.concat(" ").concat(blake.lastName);
            x.phone = blake.phone;
            x.email = blake.email;
            x.rsvpStatus = "Host";
            invites.save(x);

            userBuild.stream().forEach(user -> {
                Invite i = new Invite();
                i.party = grad;
                i.user = user;
                i.rsvpStatus = "Maybe";
                i.phone = user.phone;
                i.email = user.email;
                i.name = user.firstName.concat(" ").concat(String.valueOf(user.lastName.charAt(0)).toUpperCase()).concat(".");
                invites.save(i);
            });
        }
    }

    public static String readFile(String fileName) {
        File f = new File(fileName);
        try {
            FileReader fr = new FileReader(f);
            int fileSize = (int) f.length();
            char[] fileContent = new char[fileSize];
            fr.read(fileContent);
            return new String(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void newInvite(Invite i, InviteRepo inv, Party p) {
        Invite invite = new Invite();
        invite.party = p;

        invite.email = i.email;
        invite.phone = i.phone.replaceAll("[^0-9]","");
        if (i.name.contains(" ")) {
            String[] newName = i.name.split(" ");
            i.name = newName[0].concat(" "+String.valueOf(newName[1].charAt(0)).toUpperCase()+".");
        }
        invite.name = i.name;
        invite.rsvpStatus = "RSVP";
        inv.save(invite);
    }

    public void updateInvitedUser(){}

    public static String[] nameSplit(String string) {
        return string.split(" ");
    }

    public void update(Party party, PartyRepo repo) {
        repo.findOne(party.partyID);
    }

    public static void msgGateway(Invite user, User host, Party party) throws MessagingException {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        JavaMailSenderImpl mailSender = ctx.getBean(JavaMailSenderImpl.class);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage);
        if (user.phone!=null) {
            user.phone = user.phone.replace("(", "").replace(")", "").replaceAll(" ", "").replaceAll("-", "").replace(" ", "").trim();
            user.phone = user.phone.replace(" ", "");
            MimeMessage attMsg = mailSender.createMimeMessage();
            MimeMessageHelper att = new MimeMessageHelper(attMsg);
            att.setFrom("schindig.app@gmail.com");
            att.setTo(user.phone+"@txt.att.net");
            att.setText("Hey "+user.name+"! "+host.firstName+" just invited you to their party! RSVP @ https://github.com/Schindig/SchindigApp!");
            mailSender.send(attMsg);
            MimeMessage vzwMsg = mailSender.createMimeMessage();
            MimeMessageHelper vzw = new MimeMessageHelper(vzwMsg);
            vzw.setFrom("schindig.app@gmail.com");
            vzw.setTo(user.phone+"@vtext.com");
            vzw.setText("Hey "+user.name+"! "+host.firstName+" just invited you to their party! RSVP @ https://github.com/Schindig/SchindigApp!");
            mailSender.send(vzwMsg);
            MimeMessage sprintMsg = mailSender.createMimeMessage();
            MimeMessageHelper sprint = new MimeMessageHelper(sprintMsg);
            sprint.setFrom("schindig.app@gmail.com");
            sprint.setTo(user.phone+"@messaging.sprintpcs.com");
            sprint.setText("Hey "+user.name+"! "+host.firstName+" just invited you to their party! RSVP @ https://github.com/Schindig/SchindigApp!");
            mailSender.send(sprintMsg);
//            MimeMessage tmoMsg = mailSender.createMimeMessage();
//            MimeMessageHelper tmo = new MimeMessageHelper(tmoMsg);
//            tmo.setFrom("schindig.app@gmail.com");
//            tmo.setReplyTo(host.email);
//            tmo.setTo(user.phone+"@tmomail.net");
//            tmo.setText("Hey! "+host.firstName+" just invited you to their party! Go to http://www.schindig.com/app to RSVP!");
//            mailSender.send(tmoMsg);
        }
//        if (user.email!=null) {
//            mailMsg.setFrom("schindig.app@gmail.com");
//            mailMsg.setReplyTo(host.email);
//            mailMsg.setTo(user.email);
//            mailMsg.setSubject(host.firstName+" just invited you to their party!");
//            mailMsg.setText("Hello World!");
//            mailSender.send(mimeMessage);
//        }
//        System.out.println("---Done---");
    }

    public static Boolean initApp(String device, AuthRepo arepo) {
        Auth a = arepo.findByDevice(device);
        return a != null;
    }

    public static KeyPair keyMaker() throws NoSuchAlgorithmException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        return KeyPairGenerator.getInstance("RSA").generateKeyPair();
    }

    public static void newDevice(User user, String device, AuthRepo repo) throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        KeyPair keyPair = keyMaker();
        Auth auth = repo.findByDevice(device);
        if (auth==null) {
            final Cipher cipher = Cipher.getInstance("RSA");
            final String token = device.concat(user.username).concat(String.valueOf(user.password));
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
            byte[] encryptedToken = cipher.doFinal(token.getBytes());
            String encryptString = new String(Base64.getEncoder().encode(encryptedToken));
            Auth a = new Auth(user, device, encryptString, token);
            repo.save(a);
        }
    }

    public static Boolean validate(User user, String device, AuthRepo repo) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        KeyPair keyPair = keyMaker();
        Auth a = repo.findByDevice(device);
        if (a!=null) {
            final Cipher cipher = Cipher.getInstance("RSA");
            final String token = device.concat(user.username).concat(user.password);
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
            byte[] encryptedToken = cipher.doFinal(token.getBytes());
            String encryptString = new String(Base64.getEncoder().encode(encryptedToken));

            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            byte[] ciphertoken = Base64.getDecoder().decode(encryptString.getBytes());
            byte[] decrypt = cipher.doFinal(ciphertoken);
            String tokenTest = new String(decrypt);
            if (!a.token.equals(tokenTest)) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public static Boolean charCheck(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static Boolean phoneCheck(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static Boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public static void getVenmo(String code, User user, UserRepo repo) throws IOException {

        String url = Venmo.getVenmoTokenURL();
        URL object = new URL(url);
        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json; charset=UTF-8");
        con.setRequestMethod("POST");

        JSONObject json = new JSONObject();

        json.put("client_id",Venmo.getVenmoID());
        json.put("client_secret", Venmo.getVenmoKey());
        json.put("code", code);

        OutputStream os = con.getOutputStream();
        os.write(json.toString().getBytes("UTF-8"));
        os.close();
        os.flush();
        String split = null;
        int HttpResult = con.getResponseCode();

        if ( HttpResult == HttpURLConnection.HTTP_OK ) {

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));
            String line = null;

            while ((line = br.readLine()) != null) {
                split = line;
            }

            br.close();

        } else {

            System.out.println(con.getResponseMessage());

        }

        JSONObject obj = new JSONObject(split);
        JSONObject userObj = obj.getJSONObject("user");
        String id = userObj.get("id").toString().trim();
        String access = obj.get("access_token").toString().trim();
        String refresh = obj.get("refresh_token").toString().trim();
        user.setVenmoID(id);
        user.setVenmoAccessToken(access);
        user.setVenmmoRefreshToken(refresh);
        repo.save(user);
    }

    public static String sendPayment(User guest, Party party, UserRepo repo, Double amount) throws IOException {
        String url = Venmo.getVenmoPaymentURL();
        URL object = new URL(url);
        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json; charset=UTF-8");
        con.setRequestMethod("POST");

        JSONObject json = new JSONObject();
        amount = .10;
        String message = String.format("Schindig Party Payment of %s made to %s's %s.", String.valueOf(amount), party.host.getFirstName(), party.getPartyName());

        json.put("access_token", guest.getVenmoAccessToken());
        if (party.host.getVenmoID()!=null) {
            json.put("user", party.host.getVenmoID());
        } else if (party.host.getEmail()!=null) {
            json.put("email", party.host.getEmail());
        } else {
            json.put("phone", party.host.getPhone());
        }
        json.put("note", message);
        json.put("amount", amount);


        OutputStream os = con.getOutputStream();
        os.write(json.toString().getBytes("UTF-8"));
        os.close();
        os.flush();
        String split = null;
        int HttpResult = con.getResponseCode();

        if ( HttpResult == HttpURLConnection.HTTP_OK ) {

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));
            String line = null;

            while ((line = br.readLine()) != null) {
                split = line;
            }

            br.close();

        } else {

            System.out.println(con.getResponseMessage());

        }
        if (split==null) {
            return "400";
        } else {
            guest.setContributions(guest.getContributions()+amount);
            repo.save(guest);
            System.out.printf("Successful Payment");
            return "200";
        }

    }

}
    

