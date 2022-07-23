package AgileExpress.Server.LDAP;

import AgileExpress.Server.Constants.UserTypes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class LDIFUser {

    private String dn = ""; //distinguished name

    private String ou = ""; //organizational unit

    private String cn = ""; //common name

    private String sn = ""; //surname

    private final String uid;

    private final String passwordHash;

    //TODO: Change organization to enum
    public LDIFUser(String uid, String passwordHash, String organization, String firstName, String surname) {
        this.uid = uid;
        this.passwordHash = "userPassword: " + passwordHash;
        this.ou = organization;
        this.cn = "cn: " + firstName + " " + surname;
        this.sn = "sn: " + surname;
        constructDnString();
    }

    private void constructDnString() {
        String[] elements = new String[4];

        elements[0] = "dn: ";
        elements[1] = "uid=" + this.uid;
        elements[2] = "ou=people";
        elements[3] = "dc=springframework,dc=org";

        this.dn = "\n\n" + String.join(",", elements).replaceFirst(",", "");
    }

    private String getUidString() {
        return "uid: " + this.uid;
    }

    private String getOutput() {
        var objectString = "objectclass: top\n" +
                "objectclass: person\n" +
                "objectclass: organizationalPerson\n" +
                "objectclass: inetOrgPerson\n";

        String output = this.dn + "\n" + objectString + this.cn + "\n" + this.sn + "\n" + this.getUidString() + "\n" + this.passwordHash;
        return output;
    }

    public void save() {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/ldap-data.ldif", true);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(this.getOutput());
            writer.close();

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
}

