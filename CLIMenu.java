import javax.swing.*;
import java.awt.*;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

/** Extension GUI for Coursework 3
 * Extends a todos application with Swing toolkit.
 * @author Arkadiusz Nowacki
 * @version 15.0
 */
public class CLIMenu extends JFrame{
    ArrayList<Todo> todos = new ArrayList<>();
    JList   todoJList;
    JScrollPane scrollTodoL;
    JPanel  buttonPanel,
            displayTextPanel;
    JLabel  selectedName,
            selectedDate,
            selectedCategory,
            selectedImportance,
            selectedStatus;
    JButton add,
            update,
            delete,
            exit;

    /* index of selected item from JList */
    int idx = -1;

    /* ListModel used for easier
    manipulation/presentation of
    todos in JList */
    DefaultListModel listModel;

    /** Constructor for this class **/
    public CLIMenu(){
        /* Setting up the JFrame */
        /* Width (x) and height (y) for quicker centering of JFrame */
        int x = 600, y = 500;
        setTitle("Todo List");
        setSize(x,y);
        Color ourC = new Color(187, 194, 193);
        setBackground(ourC);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        /* JFrame appears on the centre of our desktop (using this x and y values) */
        setLocation(dim.width/2-this.getPreferredSize().width/2 - x/2, dim.height/2-this.getPreferredSize().height/2  -y/2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,3,3,3));

        /* Button Panel && display text panel
        + additional setting up */
        buttonPanel = new JPanel();
        displayTextPanel = new JPanel();
        displayTextPanel.setBackground(ourC);
        buttonPanel.setLayout(new GridLayout(4,1,7,7));
        buttonPanel.setBackground(ourC);

        /* New instance of JList && DefaultListModel
         + additional setting up */
        listModel = new DefaultListModel();
        todoJList = new JList(listModel);
        todoJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        /* Works when JList is full
         (horizontal and vertical scroll). */
        scrollTodoL = new JScrollPane(todoJList);
        scrollTodoL.setPreferredSize(new Dimension(250, 300));

        /* List Selection Listeners as Lambda expression */
        todoJList.addListSelectionListener(e -> {
            idx = todoJList.getSelectedIndex();
            /* -1 value as getSelectedIndex()
            returns -1 while nothing is selected and
            causes IndexOutOfBoundsException */
            if(idx != -1) {
                /* Category chosen by user changes the color of the todos */
                String catColor = String.valueOf(todos.get(idx).getCat()).toLowerCase();
                selectedName.setText("<html><h2><font color=" + catColor +  ">" + todos.get(idx).getText() + "</font></h2></html>");
                selectedDate.setText("<html><h2><font color=" + catColor +  ">" + todos.get(idx).getDue() + "</font></h2></html>");
                selectedImportance.setText("<html><h2><font color=" + catColor +  ">" + todos.get(idx).getImportance() + "</font></h2></html>");
                selectedStatus.setText("<html><h2><font color=" + catColor +  ">" + todos.get(idx).getCompletion() + "</font></h2></html>");
            }
        });

        /* Make todos appear at the centre of JList */
        DefaultListCellRenderer renderer =  (DefaultListCellRenderer) todoJList.getCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        /* New instance of JButtons && JLabels */
        add = new JButton("<html><h1>" + "Add" + "</h1></html>");
        update = new JButton("<html><h1>" + "Update one" + "</h1></html>");
        delete = new JButton("<html><h1>" + "Delete one" + "</h1></html>");
        exit = new JButton("<html><h1>" + "Exit" + "</h1></html>");
        selectedName = new JLabel();
        selectedDate = new JLabel();
        selectedCategory = new JLabel();
        selectedImportance = new JLabel();
        selectedStatus = new JLabel();

        /* Action Listeners as Lambda expressions */
        add.addActionListener(e  -> {
            if(e.getSource().equals(add)) {
                addTodo();
            }
        });
        update.addActionListener(e -> {
            if(e.getSource().equals(update)) {
                update();
            }
        });
        delete.addActionListener(e -> {
            if(e.getSource().equals(delete)) {
                delete();
            }
        });
        exit.addActionListener(e -> {
            if(e.getSource().equals(exit)) {
                System.exit(1);
            }
        });

        /* Add Panel for buttons */
        add(buttonPanel);

        /* Add JScrollPane && Panel to display text */
        add(scrollTodoL);
        add(displayTextPanel);

        /* Add JButtons */
        Arrays.asList(add, update, delete, exit).forEach(jButton -> buttonPanel.add(jButton));

        /* Add JLabels */
        Arrays.asList(selectedName, selectedDate, selectedCategory, selectedImportance, selectedStatus).forEach(jLabel -> displayTextPanel.add(jLabel));

        /* Set visibility */
        setVisible(true);
    }

    /** FUNCTIONALITIES **/
    public void addTodo(){
        /* Text fields and combo boxes for questions */
        JTextField title = new JTextField();
        JTextField date = new JTextField();
        JComboBox<Category> category = new JComboBox<>(Category.values());
        JComboBox<Importance> importance = new JComboBox<>(Importance.values());
        JComboBox<Status> status = new JComboBox<>(Status.values());
        /* Object for JOptionPane */
        Object [] fields = {
                "Title ", title,
                "Due date (in format: YYYY-MM-DDTHH:MM ", date,
                "Category ", category,
                "Importance ", importance,
                "Status ", status
        };
        /* Option pane displays the questions related to new todos and check for date format */
            try {
                JOptionPane.showConfirmDialog(null,fields,"Add Todo", JOptionPane.YES_NO_OPTION);
                Todo addedTodo = new Todo(title.getText(),
                        LocalDateTime.parse(date.getText()),
                        Category.valueOf(category.getSelectedItem().toString()),
                        Importance.valueOf(importance.getSelectedItem().toString()),
                        Status.valueOf(status.getSelectedItem().toString()));
                listModel.addElement(addedTodo.getText());
                todos.add(addedTodo);
            } catch (DateTimeException dateErr){
                System.out.println("The error that happens is " + "|| " + dateErr + " ||");
                JOptionPane.showMessageDialog(null, "Empty date or wrong format, please try again. ", "Warning!", JOptionPane.WARNING_MESSAGE);
            }catch (NullPointerException nullErr) {
                System.out.println("The error that happens is " + "|| " + nullErr + " ||");
                JOptionPane.showMessageDialog(null, "Null value from dialog! ", "Error!", JOptionPane.ERROR_MESSAGE);
            }
    }

    /** Update todos. Does not work while there
     *  is no todos added, which could be selected.
     *  Also the change may not be visible if we
     *  do not deselect the item **/
    public void update() {
        if(idx != -1){
            /* Text fields and combo boxes for questions */
            JTextField title = new JTextField();
            JTextField date = new JTextField();
            JComboBox<Category> category = new JComboBox<>(Category.values());
            JComboBox<Importance> importance = new JComboBox<>(Importance.values());
            JComboBox<Status> status = new JComboBox<>(Status.values());
            /* Object for JOptionPane */
            Object[] fields = {
                    "Title ", title,
                    "Due date (in format: YYYY-MM-DDTHH:MM ", date,
                    "Category ", category,
                    "Importance ", importance,
                    "Status ", status
            };
            /* Option pane displays the questions related to updating existing todos and check for date format */
            try {
                JOptionPane.showConfirmDialog(null, fields, "Update Todo", JOptionPane.YES_NO_OPTION);
                Todo addedTodo = new Todo(title.getText(),
                        LocalDateTime.parse(date.getText()),
                        Category.valueOf(category.getSelectedItem().toString()),
                        Importance.valueOf(importance.getSelectedItem().toString()),
                        Status.valueOf(status.getSelectedItem().toString()));
                listModel.set(idx, addedTodo.getText());
                todos.set(idx, addedTodo);
            } catch (DateTimeException dateErr) {
                System.out.println("The error that happens is " + "|| " + dateErr + " ||");
                JOptionPane.showMessageDialog(null, "Empty date or wrong format, please try again. ", "Warning!", JOptionPane.WARNING_MESSAGE);
            } catch (NullPointerException nullErr) {
                System.out.println("The error that happens is " + "|| " + nullErr + " ||");
                JOptionPane.showMessageDialog(null, "Null value from dialog! ", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /** Delete todos. Does not work while there
     *  is no todos added, which could be selected **/
    public void delete(){
        int idxOfChosen = todoJList.getSelectedIndex();
        /* -1 value as getSelectedIndex()
        returns -1 while nothing is selected and
        causes IndexOutOfBoundsException */
        if(idxOfChosen != -1) {
            listModel.removeElementAt(idxOfChosen);
            todos.remove(idxOfChosen);
        }
    }
}
