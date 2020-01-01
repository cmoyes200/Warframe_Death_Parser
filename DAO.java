import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DAO {

    private ArrayList<String> data = new ArrayList<>();
    private boolean named = false;
    private String name;
    private String start;

    public void load() {
        String path = System.getenv("LOCALAPPDATA");
        path += "\\Warframe\\EE.log";
        System.out.println("Accessing log at " + path);

        try {
            File file = new File(path);
            BufferedReader in = new BufferedReader(new FileReader(file));

            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains("Current time:")) {
                    this.start = startFormatter(line);
                }
                if (!named) {
                    if (line.contains("Logged in")) {
                        this.name = nameFormatter(line);
                        this.named = true;
                    }
                }
                if (line.contains("was killed") || line.contains("was downed")) {
                    data.add(timeStamper(line));
                }
            }

    } catch (IOException ioe) {
            System.out.println("Unable to read from file: " + ioe.getMessage());
        }
    }

    public ArrayList<String> getData() {
        return this.data;
    }

    public String getName() {
        return this.name;
    }

    public String getStart() {
        return this.start;
    }

    public String startFormatter(String start) {
        String[] startarray = start.split("Current time: ", 2);
        String split = startarray[startarray.length-1];
        startarray = split.split("\\s+",0);

        String month = getMonthNumber(startarray[1]);
        String day = startarray[2];
        String year = startarray[4];
        String time = startarray[3];

        String date = "LOG DATE - " + day + "/" + month + "/" + year + ", " + time;
        return date;
    }

    public String nameFormatter(String name) {
        String[] namearray = name.split("Logged in ", 2);
        name = namearray[namearray.length-1];
        namearray = name.split("\\s+", 2);
        name = "Player identified as <" + namearray[0] + ">";
        return name;
    }

    public String getMonthNumber(String month) {
        switch (month) {
            case "Jan":
                month = "1";
                break;
            case "Feb":
                month = "2";
                break;
            case "Mar":
                month = "3";
                break;
            case "Apr":
                month = "4";
                break;
            case "May":
                month = "5";
                break;
            case "Jun":
                month = "6";
                break;
            case "Jul":
                month = "7";
                break;
            case "Aug":
                month = "8";
                break;
            case "Sep":
                month = "9";
                break;
            case "Oct":
                month = "10";
                break;
            case "Nov":
                month = "11";
                break;
            case "Dec":
                month = "12";
                break;
        }

        return month;
    }

    public String timeStamper(String line) {
        String[] linearray = line.split("\\.", 2);
        String time = linearray[0];
        String message = linearray[1].split("]: ",2)[1];

        Integer starthour = startTimeFromDate(this.start)[0];
        Integer startmin = startTimeFromDate(this.start)[1];
        Integer startsec = startTimeFromDate(this.start)[2];
        Integer newtime = Integer.parseInt(time);

        Integer totalsec = startsec+newtime;

        while (totalsec>=60) {
            totalsec = totalsec-60;
            startmin = startmin+1;
        }
        while (startmin>=60) {
            startmin = startmin-60;
            starthour = starthour+1;
        }
        while (starthour>=24) {
            starthour = starthour-24;
        }

        String starthourstring = String.format("%02d", starthour);
        String startminstring = String.format("%02d", startmin);
        String totalsecstring = String.format("%02d", totalsec);

        String output = starthourstring + ":" + startminstring + ":" + totalsecstring + " - " + message;
        return output;
    }

    public Integer[] startTimeFromDate(String date) {
        String time = date.split(", ",2)[1];
        String[] stringtimearray = time.split(":",0);
        Integer[] timearray = new Integer[3];
        for (int i = 0; i<timearray.length; i++) {
            timearray[i] = Integer.parseInt(stringtimearray[i]);
        }
        return timearray;
    }
}