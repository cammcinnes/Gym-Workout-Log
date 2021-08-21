package ui;

import model.AllWorkouts;
import model.Exercise;
import ui.sound.Sound;
import model.Workout;
import persistence.JsonReader;
import persistence.JsonWriter;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// user interface for AllWeeks
//  Citation: constant JSON_STORE was used from JsonSerializationDemo WorkRoomApp
//            code in start, saveAllWorkouts and loadAllWorkouts is modeled by
//            JsonSerializationDemo WorkRoomApp class
//
public class StrengthWorkoutsApp {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private static final String JSON_STORE = "./data/allWorkouts.json";

    private AllWorkouts myWorkouts;
    private DefaultListModel<Workout> model;
    private Scanner in;

    private JSplitPane splitPane;
    private JPanel info;
    private JPanel buttons;
    private JLabel label;
    private JList<Workout> list;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Sound sound;


    // EFFECTS: runs and initializes user interface
    public StrengthWorkoutsApp() {
        initializeAllWorkouts();
        initializeGUI();
        runStrengthWorkoutsAppOrQuit();
    }

    // EFFECTS: initialize AllWorkouts
    private void initializeAllWorkouts() {
        myWorkouts = new AllWorkouts("My Workouts");
        model = new DefaultListModel<>();
        in = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

    }

    // MODIFIES: this
    // EFFECTS: draws the JFrame window with components inside
    private void initializeGUI() {
        sound = new Sound();
        listInit();
        createButtons();
        panelInit();
        addToSplitPane();
        frameInit();
    }

    // MODIFIES: this
    // EFFECTS: instantiates Buttons and draws a JPanel with inserted buttons
    private void createButtons() {
        buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        buttons.setSize(new Dimension(0, 0));

        JButton create = new JButton("Create New Workout");
        create.setVerticalTextPosition(AbstractButton.CENTER);
        create.setHorizontalTextPosition(AbstractButton.CENTER);
        create.addActionListener(new NewWorkoutTool());
        buttons.add(create);

        JButton save = new JButton("Save Workouts");
        save.setVerticalTextPosition(AbstractButton.CENTER);
        save.setHorizontalTextPosition(AbstractButton.CENTER);
        save.addActionListener(new NewSaveTool());
        buttons.add(save);

        JButton load = new JButton("Load Workouts");
        load.setVerticalTextPosition(AbstractButton.CENTER);
        load.setHorizontalTextPosition(AbstractButton.CENTER);
        load.addActionListener(new NewLoadTool());
        buttons.add(load);
    }

    // action listener for when save button is pressed
    private class NewSaveTool implements ActionListener {

        // EFFECTS: saves all workouts to JSON file when the button save is pressed and plays sound
        @Override
        public void actionPerformed(ActionEvent e) {
            saveAllWorkouts();
            sound.playSound("button7.wav");
        }
    }

    // action listener for when load button is pressed
    private class NewLoadTool implements ActionListener {

        // EFFECTS: loads all workouts from JSON file when the button load is pressed and plays sound
        @Override
        public void actionPerformed(ActionEvent e) {
            loadAllWorkouts();
            sound.playSound("button7.wav");
        }
    }

    // action listener for when create workout button is pressed
    private class NewWorkoutTool implements ActionListener {
        private JPanel popupContent;
        private JButton update;
        private JLabel dateLabel;
        private JLabel timeLabel;
        private JTextField dateText;
        private JTextField durationText;


        // MODIFIES: this
        // EFFECTS: creates a new workout by creating a new window to process user input.
        @Override
        public void actionPerformed(ActionEvent e) {
            textFieldInit();
            update();
            contentInit();
            frame2Init();
        }

        // MODIFIES: this
        // EFFECTS: instantiates two text fields and labels
        private void textFieldInit() {
            dateText = new JTextField(20);
            durationText = new JTextField(20);
            dateLabel = new JLabel("Date:", JLabel.LEFT);
            timeLabel = new JLabel("Duration:", JLabel.LEFT);
        }

        // MODIFIES: this
        // EFFECTS: initializes a panel to hold dateText, durationText and update button
        private void contentInit() {
            popupContent = new JPanel();
            popupContent.setLayout(new BoxLayout(popupContent, BoxLayout.PAGE_AXIS));
            popupContent.add(dateLabel);
            popupContent.add(dateText);
            popupContent.add(timeLabel);
            popupContent.add(durationText);
            popupContent.add(update);
        }

        // MODIFIES: this
        // EFFECTS: draws a JFrame window where splitPane2 is displayed
        private void frame2Init() {
            JFrame popup = new JFrame("New Workout");
            popup.add(popupContent);
            popup.setMinimumSize(new Dimension(WIDTH / 4, HEIGHT / 4));
            popup.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            popup.setLocationRelativeTo(null);
            popup.setVisible(true);
        }

        // MODIFIES: this
        // EFFECTS: instantiates a button
        private void update() {
            update = new JButton("Update");
            update.setVerticalTextPosition(AbstractButton.CENTER);
            update.setHorizontalTextPosition(AbstractButton.CENTER);
            update.addActionListener(new UpdateTool());
        }

        // action listener for update button
        private class UpdateTool implements ActionListener {

            // EFFECTS: gets text from date and duration and creates new workout; plays sound when pressed
            @Override
            public void actionPerformed(ActionEvent e) {
                String date = dateText.getText();
                int duration = Integer.parseInt(durationText.getText());
                createNewWorkout(date, duration);
                sound.playSound("button7.wav");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes panel
    private void panelInit() {
        info = new JPanel(new BorderLayout());
        label = new JLabel();
        info.add(label, BorderLayout.NORTH);
        info.add(buttons, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: draws the JFrame window where the SplitPane will be displayed
    private void frameInit() {
        JFrame frame = new JFrame("Strength Workouts");
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.add(splitPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: instantiates a list as a JList by setting to default list model
    //           and adding a selection event
    private void listInit() {
        list = new JList<>();
        list.setModel(model);
        list.getSelectionModel().addListSelectionListener(e -> {
            Workout w = list.getSelectedValue();
            label.setText("Workout -- Date: " + w.getDate() + ", Duration: "
                    + w.getMinutes() + " minutes");
        });

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(7);
        list.setMinimumSize(new Dimension(0, 0));

    }

    // MODIFIES: this
    // EFFECTS: instantiates a JSplitPane and adds list and panel as a component
    private void addToSplitPane() {
        splitPane = new JSplitPane();
        splitPane.setTopComponent(new JScrollPane(list));
        splitPane.setBottomComponent(info);
    }

    // MODIFIES: this
    // EFFECTS: processes user input for Workouts
    private void runStrengthWorkoutsAppOrQuit() {
        boolean kg = true;
        String button;

        while (kg) {
            displayWorkoutsMenu();
            button = in.next();
            button = button.toLowerCase();

            if (button.equals("q")) {
                kg = false;

            } else {
                processAllWorkoutsButton(button);
            }

        }
        System.out.println("Application has been quit.");
    }

    // EFFECTS: display AllWorkouts and commands
    private void displayWorkoutsMenu() {
        System.out.println("Press:");
        System.out.println("c --> create new workout");
        System.out.println("p --> print all workouts");
        System.out.println("a --> save all workouts to file");
        System.out.println("l --> load all workouts from file");
        System.out.println("s --> select workout");
        System.out.println("q --> quit");

    }

    // MODIFIES: this
    // EFFECTS: processes commands for AllWorkout
    private void processAllWorkoutsButton(String input) {

        switch (input) {
            case "c":
                processNewWorkoutCommands();
                break;
            case "p":
                printWorkouts();
                break;
            case "a":
                saveAllWorkouts();
                break;
            case "l":
                loadAllWorkouts();
                break;
            case "s":
                displayWorkoutSelectionMenu(myWorkouts);
                selectWorkout();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    // EFFECTS: processes commands for new workout
    private void processNewWorkoutCommands() {
        String date;
        int minutes;

        in.nextLine();
        System.out.println("Insert date:");
        date = in.nextLine();

        System.out.println("Insert mins: ");
        minutes = in.nextInt();

        createNewWorkout(date, minutes);
    }

    // MODIFIES: this, model
    // EFFECTS: creates a new workout given date and minutes and adds to model and myWorkouts
    private void createNewWorkout(String date, int minutes) {
        Workout w1 = new Workout(minutes, date);

        myWorkouts.addWorkout(w1);
        model.addElement(w1);
    }

    //EFFECTS: prints all workout in AllWorkouts to console
    private void printWorkouts() {
        List<Workout> myW = myWorkouts.getWorkouts();

        for (Workout w : myW) {
            System.out.println("Workout " + myW.indexOf(w) + ":");
            System.out.println("Date: " + w.getDate());
            System.out.println("Duration: " + w.getMinutes() + " mins" + "\n");
        }
    }

    // EFFECTS: displays the selection menu for a workout in AllWorkouts
    private void displayWorkoutSelectionMenu(AllWorkouts myWorkouts) {
        List<Workout> myW = myWorkouts.getWorkouts();

        for (Workout w : myW) {
            System.out.println("Workout " + myW.indexOf(w));
        }
        System.out.println("int --> selects a workout of given integer");
    }


    // EFFECTS: selects the workout by processing use input
    private void selectWorkout() {
        int selection;

        selection = in.nextInt();
        Workout w1 = myWorkouts.get(selection);

        if (w1 == null) {
            System.out.println("The workout selected does not exist.");
        } else {
            runIndividualWorkout(w1);
        }
    }

    // MODIFIES: w1
    // EFFECTS: process user input for a Workout interface
    private void runIndividualWorkout(Workout w1) {
        boolean kg2 = true;
        String button2;


        while (kg2) {
            displayWorkoutCommands(w1);

            button2 = in.next();
            button2 = button2.toLowerCase();

            if (button2.equals("q")) {
                kg2 = false;
            } else if (button2.equals("p")) {
                displayDescriptions(w1);
            } else {
                exerciseCommands(w1);
            }
        }
    }

    // EFFECTS: displays menu and commands for workout
    private void displayWorkoutCommands(Workout w) {
        System.out.println("Workout " + (myWorkouts.getOrder() - 1));
        System.out.println("Date: " + w.getDate());
        System.out.println("Duration: " + w.getMinutes() + " mins" + "\n");
        System.out.println("e --> add an exercise to the description");
        System.out.println("p --> print out exercises");
        System.out.println("q --> press q to quit" + "\n");

    }

    // EFFECTS: displays all exercises in description
    private void displayDescriptions(Workout w) {
        System.out.println("Description: ");
        for (Exercise e : w.getDescription()) {
            System.out.println("  - " + e.getSets() + "x" + e.getReps() + " " + e.getName() + "\n");

        }
    }

    // MODIFIES: w1
    // EFFECTS: process commands for a new exercise
    private void exerciseCommands(Workout w1) {
        String name;
        int reps;
        int sets;

        in.nextLine();
        System.out.println("Name of exercise?");
        name = in.nextLine();

        System.out.println("Number of reps?");
        reps = in.nextInt();

        System.out.println("Number of sets?");
        sets = in.nextInt();

        Exercise e1 = new Exercise(name, sets, reps);
        w1.addExercise(e1);
    }


    // EFFECTS: saves all workouts to file
    //          this code is modelled from code in JsonSerializationDemo
    private void saveAllWorkouts() {
        try {
            jsonWriter.open();
            jsonWriter.write(myWorkouts);
            jsonWriter.close();
            System.out.println("Saved " + myWorkouts.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this, model
    // EFFECTS: loads all workouts from file to this and model
    //          this code is modelled from code in JsonSerializationDemo
    private void loadAllWorkouts() {
        try {
            myWorkouts = jsonReader.read();
            for (Workout w : myWorkouts.getWorkouts()) {
                model.addElement(w);
            }
            System.out.println("Loaded " + myWorkouts.getName() + " from" + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read this from file: " + JSON_STORE);
        }
    }

}
