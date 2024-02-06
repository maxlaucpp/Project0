import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinalProject {
    JPanel foodPanel;
    JPanel drinkPanel;
    JPanel drinks;
    JPanel paymentPanel;
    DefaultListModel<String> listModel;
    JLabel totalLabel;

    FinalProject() {
        JFrame jfrm = new JFrame("McGUI's GUI");
        jfrm.setSize(800,600);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //creating panel
        JPanel panel = new JPanel(new BorderLayout());
        foodPanel = new JPanel();
        drinkPanel = new JPanel(new BorderLayout());
        paymentPanel = new JPanel();

        //create splitPane
        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(350);

        //creating tab pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Food",foodPanel);
        tabbedPane.addTab("Drink",drinkPanel);
        tabbedPane.addTab("Payment",paymentPanel);

        //Food Panel-----
        setMenuItem("Cheeseburger", "FoodImages/cheeseburger.jpg", foodPanel, 10);
        setMenuItem("Double Cheeseburger", "FoodImages/double_cheeseburger.jpg", foodPanel, 10);

        //set fry
        char[] frySize = {'S', 'M', 'L'};
        double[] fryCost = {5.00, 6.00, 7.00};
        for (int i = 0; i < 3; i++) {
            setMenuItem(frySize[i] + " Fries", "FoodImages/fries.jpg", foodPanel, fryCost[i]);
        }

        setMenuItem("Cheeseburger Combo", "FoodImages/cheeseburger_combo.jpg", foodPanel, 10);
        setMenuItem("Double Cheeseburger Combo", "FoodImages/double_cheeseburger_combo.jpg", foodPanel, 10);

        //drink Panel-----

        //create drinks panel
        drinks = new JPanel(new GridLayout(4,4,1,1));

        //create buttons
        setMenuItem("Cola", "FoodImages/cola.PNG", drinkPanel, 3.00);
        setMenuItem("Pepsi", "FoodImages/pepsi.jpg", drinkPanel, 3.00);
        setMenuItem("Sprite", "FoodImages/sprite.jpg", drinkPanel, 3.00);


        drinkPanel.add(drinks,BorderLayout.NORTH); //add buttons to drink panel

        //buttons for drink panel(sizes)-----
        ButtonGroup sizeGroup = new ButtonGroup();
        JPanel sizePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        small = new JRadioButton("Small");
        sizeGroup.add(small);
        sizePanel.add(small);

        medium = new JRadioButton("Medium");
        sizeGroup.add(medium);
        sizePanel.add(medium);

        large = new JRadioButton("Large");
        sizeGroup.add(large);
        sizePanel.add(large);

        drinkPanel.add(sizePanel,BorderLayout.SOUTH);

        //payment panel-----
        ButtonGroup bg = new ButtonGroup();

        String[] paymentType = {"Credit Card", "Venmo", "Cash"};
        for (String s : paymentType) {
            setPaymentTypes(s, bg); //sets payment types
        }

        //creating labels
        JLabel shopping_cart = new JLabel("Shopping Cart");
        shopping_cart.setFont(setDefaultFont(25));

        //button panel for right component
        JPanel buttonPanel = new JPanel();

        //setting button panel to be aligned along left side of right panel
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        //clear and checkout buttons
        JButton clearBtn = new JButton("Clear");
        JButton checkOutBtn = new JButton("Check Out");
        totalLabel = new JLabel("Total Cost: $0.00");
        clearBtn.setPreferredSize(new Dimension(150,50));
        checkOutBtn.setPreferredSize(new Dimension(150,50));

        listModel = new DefaultListModel<>();
        JList<String> itemList = new JList<>(listModel);
        JScrollPane boughtItemPanel = new JScrollPane(itemList);
        panel.add(boughtItemPanel);

        buttonPanel.add(totalLabel);
        buttonPanel.add(checkOutBtn);
        buttonPanel.add(clearBtn);

        clearBtn.addActionListener(new ClearButtonClickListener());
        checkOutBtn.addActionListener(new CheckoutButtonClickListener());

        //adding buttonPanel to the bottom of the panel
        panel.add(buttonPanel,BorderLayout.SOUTH);

        //adding label to panels
        panel.add(shopping_cart,BorderLayout.NORTH);

        //adding respective pane to respective side
        splitPane.setLeftComponent(tabbedPane);
        splitPane.setRightComponent(panel);

        //add to frame
        jfrm.add(splitPane);
        jfrm.add(splitPane);

        //set visible
        jfrm.setVisible(true);
    }
    JRadioButton small;
    JRadioButton medium;
    JRadioButton large;
    //default font generator for the java file
    public Font setDefaultFont (int font) {
        return new Font ("Molto",Font.ITALIC,font);
    }
    public void setMenuItem(String foodName, String filePath, JPanel menuPanel, double cost) {
        if (menuPanel.equals(foodPanel)) {
            foodItem(foodName, filePath, cost);
        }
        else if (menuPanel.equals(drinkPanel)) {
            drinkItem(foodName, filePath, cost);
        }
    }
    private void foodItem(String foodName, String filePath, double cost) {
        //creates image icon
        ImageIcon foodIcon = new ImageIcon(getClass().getResource(filePath));
        //create JButton
        JButton foodButton = new JButton(foodName, foodIcon);
        //align text position
        foodButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        foodButton.setHorizontalTextPosition(SwingConstants.CENTER);

        //set action listener
        foodListener(foodButton, cost);

        //add to food Panel
        foodPanel.add(foodButton);
    }
    private void foodListener(JButton foodButton, double cost) {
        foodButton.addActionListener(e -> {
            listModel.addElement("Food: " + foodButton.getText() + " - Cost: $" + cost);
            totalCost += cost;
            totalLabel.setText("Total Cost: $" + totalCost);
        });
    }
    private void drinkItem(String drinkName, String filePath, double cost) {
        //create image icon
        ImageIcon drinkImg = new ImageIcon(getClass().getResource(filePath));

        //create JButton
        JButton drinkButton = new JButton(drinkImg);
        drinkButton.setName(drinkName);

        //get preferred image size
        drinkButton.setPreferredSize(new Dimension(100,100));

        //add drink listener
        drinkListener(drinkButton);

        //add to panel
        drinks.add(drinkButton);
    }
    double totalCost;
    private void drinkListener(JButton drink) {
        drink.addActionListener(e -> {
            double drinkCost = 0;
            if (small.isSelected()) {
                drinkCost= 2;
                listModel.addElement("Small Drink: " + drink.getName() + " - Cost: $" + drinkCost);

            } else if (medium.isSelected()) {
                drinkCost= 3.50;
                listModel.addElement("Medium Drink: " + drink.getName() + " - Cost: $" + drinkCost);
            } else if (large.isSelected()) {
                drinkCost= 4;
                listModel.addElement("Large Drink: " + drink.getName() + " - Cost: $" + drinkCost);
            }

            totalCost += drinkCost;
            totalLabel.setText("Total Cost: $" + totalCost);
        });
    }
    public void setPaymentTypes(String paymentType, ButtonGroup buttonGroup){
        paymentTypes(paymentType, buttonGroup);
    }
    private void paymentTypes(String paymentType, ButtonGroup buttonGroup) {
        JRadioButton paymentTypeButton = new JRadioButton(paymentType);
        paymentTypeButton.setFont(setDefaultFont(25));
        buttonGroup.add(paymentTypeButton);
        paymentPanel.add(paymentTypeButton);
    }
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            totalLabel.setText("Total Cost: $" + totalCost);
        }
    }
    private class ClearButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            listModel.clear();
            totalCost = 0;
            totalLabel.setText("Total Cost: $0.00");
        }
    }

    private class CheckoutButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            listModel.addElement("Plus 7% tax");
            double result;
            result = totalCost + totalCost * 0.07;
            totalLabel.setText("Total Cost: $" + result);
        }
    }
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(FinalProject::new);
    }
}
 
