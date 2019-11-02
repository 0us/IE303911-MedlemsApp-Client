package no.ntnu.klubbhuset.ui.userviews.club;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import no.ntnu.klubbhuset.data.CommunicationConfig;
import no.ntnu.klubbhuset.data.model.Club;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static no.ntnu.klubbhuset.data.CommunicationConfig.API_URL;
import static no.ntnu.klubbhuset.data.CommunicationConfig.GET_CLUBS_URL;

public class ClubsViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    MutableLiveData<List<Club>> clubs;

    RequestQueue requestQueue;


    public ClubsViewModel(Application context) {
        super(context);
        requestQueue = Volley.newRequestQueue(context);
    }

    public LiveData<List<Club>> getClubs() {
        if (clubs == null) {
            clubs = new MutableLiveData<>();
            loadClubs();
        }
        return clubs;
    }

    protected void loadClubs() {
        String url = API_URL + GET_CLUBS_URL;
        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<Club> clubs = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            clubs.add(new Club(response.getJSONObject(i)));
                        }
                    } catch (JSONException jex) {
                        System.out.println(jex);
                    }
                    this.clubs.setValue(clubs);
                }, System.out::println);
        requestQueue.add(jar);
    }
}
