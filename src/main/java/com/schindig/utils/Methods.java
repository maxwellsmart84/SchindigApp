package com.schindig.utils;
import com.schindig.AppConfig;
import com.schindig.controllers.MainController;
import com.schindig.entities.*;
import com.schindig.services.*;
import org.json.JSONObject;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.crypto.*;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.*;
import java.util.*;


/**
 * Created by Agronis on 12/9/15.
 */
public class Methods extends MainController {

    public static void script(UserRepo users, FavorRepo favors) {
        User josh = new User();
        josh.lastName = "Roberson";
        josh.firstName = "Joshua";
        josh.username = "agronis";
        josh.email = "agronis@icloud.com";
        josh.phone = "8438643494";
        josh.password = "agronis";

        User eliz = new User();
        eliz.firstName = "Elizabeth";
        eliz.lastName = "Lewis";
        eliz.username = "erlewis";
        eliz.password = "elizabeth";
        eliz.phone = "8034644711";
        eliz.email = "erlewis288@gmail.com";

        User blake = new User("blake182", "pass", "Blake", "Guillo", "erlewis288@gmail.com", "8034644711");
        User max = new User("max", "pass", "Max", "Krause", "email", "phone");
        users.save(blake);
        users.save(max);
        users.save(eliz);
        users.save(josh);

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
        invite.phone = i.phone.replace("(", "").replace(")", "").replace("-", "").replace(" ", "").replace("+1", "");
        if (i.name.contains(" ")) {
            String[] newName = i.name.split(" ");
            i.name = newName[0].concat(" "+String.valueOf(newName[1].charAt(0)).toUpperCase()+".");
        }
        invite.name = i.name;
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
            MimeMessage attMsg = mailSender.createMimeMessage();
            MimeMessageHelper att = new MimeMessageHelper(attMsg);
            att.setFrom("schindig.app@gmail.com");
            att.setTo(user.phone+"@txt.att.net");
            att.setReplyTo(host.email);
            att.setText("Hey "+user.name+"! "+host.firstName+" just invited you to their party! Go to http://www.schindig.com/app to RSVP!");
            mailSender.send(attMsg);
            MimeMessage vzwMsg = mailSender.createMimeMessage();
            MimeMessageHelper vzw = new MimeMessageHelper(vzwMsg);
            vzw.setFrom("schindig.app@gmail.com");
            vzw.setReplyTo(host.email);
            vzw.setTo(user.phone+"@vtext.com");
            vzw.setText("Hey "+user.name+"! "+host.firstName+" just invited you to their party! Go to http://www.schindig.com/app to RSVP!");
            mailSender.send(vzwMsg);
            MimeMessage sprintMsg = mailSender.createMimeMessage();
            MimeMessageHelper sprint = new MimeMessageHelper(sprintMsg);
            sprint.setFrom("schindig.app@gmail.com");
            sprint.setReplyTo(host.email);
            sprint.setTo(user.phone+"@messaging.sprintpcs.com");
            sprint.setText("Hey "+user.name+"! "+host.firstName+" just invited you to their party! Go to http://www.schindig.com/app to RSVP!");
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
    

