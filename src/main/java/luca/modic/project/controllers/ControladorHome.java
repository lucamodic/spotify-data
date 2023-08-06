package luca.modic.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Controller
public class ControladorHome {

    static SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken("BQAaUbuq8nKHLY5Z-MeYJHTqbvGPHT1TolsSHv0n7e2Vl_Rc0xMBK0GBgLrfo3F8i8Jn0M8xvA_ejdS2vXtm3kfQr18lXbfHe_CAxE5QtkZzJBVBLJFrCA8mkG_RxwHEFAd8DgR2AwESoK-x2NG6zQT0NGkDHajFI30lNvptth1iqjMP-FjjQgj1v9JaliN3KbYqNJ1xEW070lJ6fT9oJnqJt_tE1N73BSRvxEoZmjti7FfDaexUgX6O9rHcGgdmxuaXyfQ_Sjkm9ZiUqQpPH6nDpyVeVWis3E6CmiE54qkRnza4TZ5C4fBHjXVnbvOTF4PpdSbAJR8rL_8kIBCy")
            .setRefreshToken("AQBq6e4vFKtSeSNIeMpF-OgUoc0_jKQXKpGm_aXdOpJYsF-zEyprHv18IELq-5w7HtBbfnxUol756QcODkN3uIZ0HTsOCPGz3x0CScBmgUXQ4KSVvBdjHCLlU8yHZRXYOqQ")
            .build();

    public static String id = "https://open.spotify.com/playlist/0g5oVrhS0nsa5jV49Okpao?si=a1bb4feb00c34b13";


    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView home() {
        return new ModelAndView("home");
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(path = "/show", method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest request) {

        String url = request.getParameter("url");
        ModelMap model = new ModelMap();
        model.put("url", url);

        GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi
                .getPlaylistsItems(url)
                .build();

        System.out.println("--------------------------------------");

        final String SONGS = "SONGS";
        final String ARTISTS =  "ARTISTS";

        List<String> songs = getPlaylistsItems(getPlaylistsItemsRequest, SONGS);
        List<String> artists = getPlaylistsItems(getPlaylistsItemsRequest, ARTISTS);
        if(songs != null){
            Integer length = songs.size();
            length--;
            model.put("length", length);
        }

        model.put("songs", songs);
        model.put("artists", artists);

        return new ModelAndView("show-songs",model);
    }

    public static List<String> getPlaylistsItems(GetPlaylistsItemsRequest getPlaylistsItemsRequest, String string) {
        try {
            final CompletableFuture<Paging<PlaylistTrack>> pagingFuture = getPlaylistsItemsRequest.executeAsync();
            final Paging<PlaylistTrack> playlistTrackPaging = pagingFuture.join();
            Integer totalSongs = playlistTrackPaging.getTotal();
            List<String> artistas = new ArrayList<String>();
            List<String> canciones = new ArrayList<String>();
            //ITERATE THE SONGS
            for(int i = 0; i < totalSongs; i++){
                Track track = (Track) playlistTrackPaging.getItems()[i].getTrack();

                String nombreCompleto = buscarArtistas(i, playlistTrackPaging);
                artistas.add(nombreCompleto);

                String cancion = ((String) playlistTrackPaging.getItems()[i].getTrack().getName());
                canciones.add(cancion);
            }
            if(string.equals("SONGS")){
                return canciones;
            }
            else {
                return artistas;
            }


        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
        return null;
    }

    private static String buscarArtistas(int i, Paging<PlaylistTrack> playlistTrackPaging) {
        String nombreCompleto = ((Track) playlistTrackPaging.getItems()[i].getTrack()).getArtists()[0].getName();
        int length = ((Track) playlistTrackPaging.getItems()[i].getTrack()).getArtists().length;
        for(int e = 1; e < length; e++){
            String nombre = ((Track) playlistTrackPaging.getItems()[i].getTrack()).getArtists()[e].getName();
            nombreCompleto = nombreCompleto + ", " + nombre;
        }
        return nombreCompleto;
    }


}
