package com.macehub.faculty;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.List;

public class Search extends Fragment {
    listAdapter listadapter;
    ListView listfav;
    String query = BuildConfig.FLAVOR;
    List<staff> staffslist;

    class listAdapter extends BaseAdapter {
        public Object getItem(int i) {
            return null;
        }

        public long getItemId(int i) {
            return 0;
        }

        listAdapter() {
        }

        public int getCount() {
            return Search.this.staffslist.size();
        }

        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(Search.this.getContext()).inflate(R.layout.itemlayout, null);
            final TextView textView = (TextView) view.findViewById(R.id.details);
            TextView textView2 = (TextView) view.findViewById(R.id.name);
            final Button button = (Button) view.findViewById(R.id.fav);

            TextView link = view.findViewById(R.id.link);
            link.setText(Search.this.staffslist.get(i).getDepartment());


            ImageView imageView = (ImageView) view.findViewById(R.id.pic);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://www.mace.ac.in/");
            stringBuilder.append(((staff) Search.this.staffslist.get(i)).getImgloc());
            Glide.with(Search.this.getContext()).load(stringBuilder.toString()).thumbnail(0.3f).transition(DrawableTransitionOptions.withCrossFade()).diskCacheStrategy(DiskCacheStrategy.ALL).override(160, 192).into(imageView);

            textView2.setText(((staff) Search.this.staffslist.get(i)).getName());
            textView.setText(((staff) Search.this.staffslist.get(i)).getDetails());



            if (((staff) Search.this.staffslist.get(i)).getFav() == 1) {
                button.setBackground(Search.this.getResources().getDrawable(R.drawable.ic_on));
            } else {
                button.setBackground(Search.this.getResources().getDrawable(R.drawable.ic_off));
            }
            ((Button) view.findViewById(R.id.call)).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    String[] split = textView.getText().toString().replaceAll("\n", ":").split(":");
                    final ArrayList arrayList = new ArrayList();
                    int length = split.length;
                    Builder builder = new Builder(Search.this.getContext());
                    for (int i = 0; i < length; i++) {
                        if (split[i].replaceAll("\\D", BuildConfig.FLAVOR).length() >= 10) {
                            arrayList.add(split[i].replaceAll("[^\\d+]", BuildConfig.FLAVOR));
                        }
                    }
                    if (arrayList.size() > 0) {
                        builder.setItems((CharSequence[]) arrayList.toArray(new CharSequence[arrayList.size()]), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent("android.intent.action.DIAL");
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("tel:");
                                stringBuilder.append((String) arrayList.get(i));
                                intent.setData(Uri.parse(stringBuilder.toString()));
                                Search.this.startActivity(intent);
                            }
                        });
                        builder.setTitle("Choose an number to dial");
                    } else {
                        builder.setMessage("No valid numbers found to dial :(").setTitle("Sorry ");
                    }
                    builder.create().show();
                }
            });
            button.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    DatabaseHandler databaseHandler = new DatabaseHandler(Search.this.getContext());
                    if (((staff) Search.this.staffslist.get(i)).getFav() == 0) {
                        ((staff) Search.this.staffslist.get(i)).setFav(1);
                        databaseHandler.setfav(1, ((staff) Search.this.staffslist.get(i)).getId());
                        databaseHandler.close();
                        button.setBackground(Search.this.getResources().getDrawable(R.drawable.ic_on));
                        Toast.makeText(Search.this.getActivity(), "Made Favourite", 0).show();
                        return;
                    }
                    ((staff) Search.this.staffslist.get(i)).setFav(0);
                    databaseHandler.setfav(0, ((staff) Search.this.staffslist.get(i)).getId());
                    databaseHandler.close();
                    button.setBackground(Search.this.getResources().getDrawable(R.drawable.ic_off));
                    Toast.makeText(Search.this.getActivity(), "Deleted From Search", 0).show();
                }
            });
            return view;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        bundle = getArguments();
        if (bundle != null) {
            this.query = bundle.getString("query");
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        getContext().getSharedPreferences("depSelected", 0).getInt("depcode", 0);
        DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
        this.staffslist = new ArrayList();
        this.staffslist = databaseHandler.getstaffsByName(this.query);
        if (this.staffslist.size() <= 0) {
            return LayoutInflater.from(getContext()).inflate(R.layout.itemmissinglayout, null);
        }
        View inflate = layoutInflater.inflate(R.layout.fragment_search, viewGroup, false);
        this.listfav = (ListView) inflate.findViewById(R.id.listsearch);
        this.listadapter = new listAdapter();
        this.listfav.setAdapter(this.listadapter);
        return inflate;
    }
}
