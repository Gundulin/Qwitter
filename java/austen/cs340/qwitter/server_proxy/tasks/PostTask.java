package austen.cs340.qwitter.server_proxy.tasks;

import android.os.AsyncTask;

import java.net.URL;

import austen.cs340.qwitter.model.Status;
import austen.cs340.qwitter.server_proxy.ServerProxy;
import austen.cs340.qwitter.server_proxy.ServerProxyInterface;

public class PostTask extends AsyncTask<URL, Integer, Boolean> {

    private austen.cs340.qwitter.model.Status status;

    public PostTask(austen.cs340.qwitter.model.Status status) {
        this.status = status;
    }

    @Override
    protected Boolean doInBackground(URL... urls) {

        ServerProxyInterface server = new ServerProxy(urls[0]);
        server.post(status);

        return Boolean.TRUE;
    }
}
