import data.Artist;
import data.DataStore;
import data.Show;

public class Main {
    public static void main(String[] args) {
        Show show = new Show("Show1", 10, 12, 100, 2 );
        DataStore dataStore = new DataStore();
        dataStore.setShow(show);

        show.addArtists(new Artist("John", "Country"));

        dataStore.setShows(show.getArtistH());

        System.out.println(dataStore.getShows().containsKey("john"));
    }
}
