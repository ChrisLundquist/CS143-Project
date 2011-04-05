/**
 * @author Tim Mikeladze
 * Open new browser with Last.FM url 
 */
package sound;

import java.util.Scanner;

public class LastFM {
    //gets OS
    private String os = System.getProperty("os.name").toLowerCase();
    private Runtime rt = Runtime.getRuntime();
    
    private boolean isPlaying = false;
    
    /**
     * Constructor is only for debugging
     */
    public LastFM() {
        Scanner s = new Scanner(System.in);
        playArtist(s.nextLine());
    }

    public void playArtist(String artist) {
        openURL("http://www.last.fm/listen/artist/"+artist);
        isPlaying = true;
    }
    public void playSong(String song) {
        openURL("http://www.last.fm/listen/artist/"+song);
        isPlaying = true;
    }
    public void playGenre(String genre) {
        openURL("http://www.last.fm/listen/genre/"+genre);
        isPlaying = true;
    }
    public void playLibrary(String username) {
        openURL("http://www.last.fm/listen/user/"+username+"/personal");
        isPlaying = true;
    }
    public void playMix(String username) {
        openURL("http://www.last.fm/listen/user/"+username+"/mix");
        isPlaying = true;
    }
    public void playRecommended(String username) {
        openURL("http://www.last.fm/listen/user/"+username+"/recommended");
        isPlaying = true;
    }
    public void playMultiTag(String tag1, String tag2, String tag3) {
        openURL("http://www.last.fm/listen#station=%2Flisten%2Ftag%2F"+tag1+"*"+tag2+"*"+tag3);
        isPlaying = true;
    }
    /**
     * Stops playback
     * This method should kill the browser process that has last.fm open
     * Not finished yet
     */
    public void stopPlayback() {
        isPlaying = false;
    }
    /**
     * Opens a url in the default browser
     * @param url
     */
    private void openURL(String url) {
        try {

            if (os.indexOf("win") >= 0) {

                // this doesn't support showing urls in the form of
                // "page.html#nameLink"
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);

            } else if (os.indexOf("mac") >= 0) {

                rt.exec("open " + url);

            } else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {

                // Do a best guess on unix until we get a platform independent
                // way
                // Build a list of browsers to try, in this order.
                String[] browsers = { "epiphany", "firefox", "chromium", "chrome",
                        "mozilla", "konqueror", "netscape", "opera", "links",
                "lynx" };

                // Build a command string which looks like
                // "browser1 "url" || browser2 "url" ||..."
                StringBuffer cmd = new StringBuffer();
                for (int i = 0; i < browsers.length; i++)
                    cmd.append((i == 0 ? "" : " || ") + browsers[i] + " \""
                            + url + "\" ");

                rt.exec(new String[] { "sh", "-c", cmd.toString() });

            } else {
                return;
            }
        } catch (Exception e) {

        }
        return;
    }
    //for debugging
    public static void main(String[]args) {
        new LastFM();
    }
}