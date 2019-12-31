import java.util.ArrayList;
import java.util.Date;

public class Repository {

    private String name;
    private String start;
    private String end;
    private ArrayList<String> data;


    public Repository(ArrayList<String> data) {
        this.data = data;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStart() {
        return this.start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getEnd() {
        return this.end;
    }

    public void add(String data) {
        this.data.add(data);
    }

    @Override
    public String toString() {
        System.out.println(getStart());
        System.out.println(getName());

        if (data.isEmpty()) {
            System.out.println("No death data found in selected log");
        }
        else {
            for (String s:data) {
                System.out.println(s);
            }
        }

        return null;
    }
}
