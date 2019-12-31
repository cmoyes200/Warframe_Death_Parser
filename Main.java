import java.util.Map;
import java.util.ArrayList;

public class Main {

    public static void main (String[] args) {
        System.out.println();
        DAO dao = new DAO();
        dao.load();
        Repository repository = new Repository(dao.getData());
        repository.setName(dao.getName());
        repository.setStart(dao.getStart());
        repository.toString();
    }
}
