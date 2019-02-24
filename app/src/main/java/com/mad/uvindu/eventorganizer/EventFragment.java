package com.mad.uvindu.eventorganizer;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class EventFragment extends Fragment {

    private MainActivity.EventAdapter eventAdapter;
    private ListView listView;

    public void setEventAdapter(MainActivity.EventAdapter eventAdapter) {
        this.eventAdapter = eventAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab = view.findViewById(R.id.fab4);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EventActivity.class);
                startActivity(intent);
            }
        });
        listView = view.findViewById(R.id.eventList);
        listView.setDivider(new ColorDrawable(getResources().getColor(R.color.colorTab4)));
        listView.setDividerHeight(1);
        listView.setAdapter(eventAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Event event = (Event) listView.getItemAtPosition(i);
                Intent intent = new Intent(getContext(), EventActivity.class);
                intent.putExtra("event", event.getEventName());
                intent.putExtra("startDate", event.getStartDate());
                intent.putExtra("endDate", event.getEndDate());
                intent.putExtra("startTime", event.getStartTime());
                intent.putExtra("endTime", event.getEndTime());
                intent.putExtra("soundMode", event.getSoundMode());
                intent.putExtra("repeatMode", event.getRepeatMode());
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Event event = (Event) listView.getItemAtPosition(i);
                Snackbar.make(view, "Delete Event " + event.getEventName() + " ?", Snackbar.LENGTH_INDEFINITE).setAction("DELETE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
                Vibrator vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(20);
                return true;
            }
        });
    }
}
