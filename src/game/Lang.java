package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yew_mentzaki
 */
public class Lang {

    ArrayList<String> an = new ArrayList<String>();
    ArrayList<String> aw = new ArrayList<String>();

    public Lang() {
        String lng = null;
        try {
            Scanner scanner = new Scanner(new File("lang.cfg"));
            lng = scanner.nextLine();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Lang.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            Scanner scanner = new Scanner(new File("res/langs/" + lng + ".lng"), "UTF-8");
            while (true) {
                String s = scanner.nextLine();
                String[] ss = s.split("=");
                an.add(ss[0]);
                aw.add(ss[1]);
            }
        } catch (Exception ex) {

        }

    }

    public String get(String an) {
        if (this.an.contains(an)) {
            return aw.get(this.an.indexOf(an));
        }
        return an;
    }
}
