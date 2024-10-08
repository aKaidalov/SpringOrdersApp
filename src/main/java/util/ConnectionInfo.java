// 3

package util;

public class ConnectionInfo {

    public final String url;
    public final String user;
    public final String pass;

    public ConnectionInfo(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "ConnectionInfo{" +
                "url='" + url + '\'' +
                ", user='" + user + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
