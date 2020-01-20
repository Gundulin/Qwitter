package austen.cs340.qwitter.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import austen.cs340.qwitter.R;
import austen.cs340.qwitter.model.Model;
import austen.cs340.qwitter.presenter.FeedPresenter;

public class FeedFragment extends Fragment {

    //private RecyclerView feedRecyclerView;
    /* Presenter */
    private FeedPresenter presenter = new FeedPresenter();
    private Button refreshButton;
    private Button loadMoreButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.feed_fragment, container, false);
        presenter.setUserAlias(Model.getInstance().getCurrentUserAlias());
        presenter.refreshFeed();
        final ListView feedListView = (ListView) v.findViewById(R.id.fragment_list_view);
        StatusListAdapter adapter = new StatusListAdapter(getActivity(), presenter.getFeed().getStatuses());
        feedListView.setAdapter(adapter);
        feedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Model.getInstance().setCurrentViewStatus(presenter.getFeed().getStatuses().get(position));
                Intent intent = StatusActivity.newIntent(getContext());
                startActivity(intent);
            }
        });
//        presenter.refreshFeed();

        refreshButton = (Button) v.findViewById(R.id.fragment_refresh_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.refreshFeed();
                StatusListAdapter adapter = new StatusListAdapter(getActivity(), presenter.getFeed().getStatuses());
                feedListView.setAdapter(adapter);
                feedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Model.getInstance().setCurrentViewStatus(presenter.getFeed().getStatuses().get(position));
                        Intent intent = StatusActivity.newIntent(getContext());
                        startActivity(intent);
                    }
                });

            }
        });

        loadMoreButton = (Button) v.findViewById(R.id.load_more_feed_button);
        loadMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadMoreFeed();
                StatusListAdapter adapter = new StatusListAdapter(getActivity(), presenter.getFeed().getStatuses());
//                feedListView = (ListView) v.findViewById(R.id.fragment_list_view);
                feedListView.setAdapter(adapter);
                feedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Model.getInstance().setCurrentViewStatus(presenter.getFeed().getStatuses().get(position));
                        Intent intent = StatusActivity.newIntent(getContext());
                        startActivity(intent);
                    }
                });

            }
        });

        return v;
    }
}
