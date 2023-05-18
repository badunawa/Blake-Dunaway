package programs;
import models.Item;
import utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class StoreMapDisplay {

    public static final int WIDTH = 700;
    public static final int HEIGHT = 500;

    public static void display(Item product) throws IOException {
        JFrame fr = new JFrame("High's Hardware Product Lookup: " + product);
        fr.setPreferredSize(new Dimension(700, 500));
        StoreMap store = new StoreMap(product.getAsileNum());
        fr.add(store);
        fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fr.pack();
        fr.setVisible(true);

    }
}



