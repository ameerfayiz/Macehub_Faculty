package com.macehub.faculty;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.TabLayoutOnPageChangeListener;
import android.support.design.widget.TabLayout.ViewPagerOnTabSelectedListener;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity<viewpager> extends AppCompatActivity implements OnNavigationItemSelectedListener  {
    private static FragmentManager fragmentManager;
    SearchAdapter SearchAdapter;
    TextView depLabel;
    String[] dep_names;
    ArrayList<String> depcodes = new ArrayList();
    int present = 0;
    ProgressDialog progressDoalog;
    int state;
    ViewStub tablayout = null;
    TabLayout tabs;
    ArrayList<String> urls = new ArrayList();
    int count = 0;
    public static final int COUNT_MIN = 300;
    boolean database_is_free=true;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.dep_names = getResources().getStringArray(R.array.dep_names);

        this.depcodes.add("ADM-CUR");
        this.depcodes.add("ADM-RET");
        this.depcodes.add("CIV-CUR");
        this.depcodes.add("CIV-RET");
        this.depcodes.add("MEC-CUR");
        this.depcodes.add("MEC-RET");
        this.depcodes.add("EEE-CUR");
        this.depcodes.add("EEE-RET");
        this.depcodes.add("ECE-CUR");
        this.depcodes.add("ECE-RET");
        this.depcodes.add("CSE-CUR");
        this.depcodes.add("CSE-RET");
        this.depcodes.add("MCA-CUR");
        this.depcodes.add("MCA-RET");
        this.depcodes.add("MAT-CUR");
        this.depcodes.add("MAT-RET");
        this.depcodes.add("SCH-CUR");
        this.depcodes.add("SCH-RET");
        this.urls.add("http://www.mace.ac.in/AdministrativeOffice.aspx?PAGE=Current&MID=170222178202231197209129154002243117141059093062");
        this.urls.add("http://www.mace.ac.in/AdministrativeOffice.aspx?PAGE=Retired&MID=001157034012012103238132130045181062233086255142");
        this.urls.add("http://www.mace.ac.in/DepartmentDetails.aspx?PAGE=Faculty&MID=031145022002123150049194212063013194065183040046");
        this.urls.add("http://www.mace.ac.in/DepartmentDetails.aspx?PAGE=Retired&MID=031145022002123150049194212063013194065183040046");
        this.urls.add("http://www.mace.ac.in/DepartmentDetails.aspx?PAGE=Faculty&MID=255242112155203016191161238238139024168208030229");
        this.urls.add("http://www.mace.ac.in/DepartmentDetails.aspx?PAGE=Retired&MID=255242112155203016191161238238139024168208030229");
        this.urls.add("http://www.mace.ac.in/DepartmentDetails.aspx?PAGE=Faculty&MID=223226139025131038127162102075040157021107168207");
        this.urls.add("http://www.mace.ac.in/DepartmentDetails.aspx?PAGE=Retired&MID=223226139025131038127162102075040157021107168207");
        this.urls.add("http://www.mace.ac.in/DepartmentDetails.aspx?PAGE=Faculty&MID=115065200076167228081205112070122060253060227254");
        this.urls.add("http://www.mace.ac.in/DepartmentDetails.aspx?PAGE=Retired&MID=115065200076167228081205112070122060253060227254");
        this.urls.add("http://www.mace.ac.in/DepartmentDetails.aspx?PAGE=Faculty&MID=006001205141207123101157008174118252000247046025");
        this.urls.add("http://www.mace.ac.in/DepartmentDetails.aspx?PAGE=Retired&MID=006001205141207123101157008174118252000247046025");
        this.urls.add("http://www.mace.ac.in/DepartmentDetails.aspx?PAGE=Faculty&MID=080113083175238225243185074243088024163070113243");
        this.urls.add("http://www.mace.ac.in/DepartmentDetails.aspx?PAGE=Retired&MID=080113083175238225243185074243088024163070113243");
        this.urls.add("http://www.mace.ac.in/DepartmentDetails.aspx?PAGE=Faculty&MID=104078139161140189145201142119196241139003039187");
        this.urls.add("http://www.mace.ac.in/DepartmentDetails.aspx?PAGE=Retired&MID=104078139161140189145201142119196241139003039187");
        this.urls.add("http://www.mace.ac.in/DepartmentDetails.aspx?PAGE=Faculty&MID=082231170030150027196177050126207130138206231244");
        this.urls.add("http://www.mace.ac.in/DepartmentDetails.aspx?PAGE=Retired&MID=082231170030150027196177050126207130138206231244");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
        this.depLabel = (TextView) findViewById(R.id.depLabel);

        Context applicationContext = getApplicationContext();
        DatabaseHandler databaseHandler = new DatabaseHandler(applicationContext);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Loaded : ");
        stringBuilder.append(String.valueOf(databaseHandler.getstaffsCount()));
        stringBuilder.append(" Staffs !");
        Toast.makeText(applicationContext, stringBuilder.toString(), Toast.LENGTH_SHORT).show();

        if (databaseHandler.getstaffsCount() < 100) {
            Builder builder = new Builder(this);
            builder.setPositiveButton(R.string.ok, new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.this.loadDb();
                }
            });
            builder.setNegativeButton(R.string.cancel, new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.this.Load_typical();
                }
            });
            builder.setMessage("I don't have enough database to show you, Can i get it from WEB please?").setTitle("Download Database please!");
            builder.setCancelable(false);
            builder.create().show();
            return;
        }
        databaseHandler.close();
        Load_typical();
    }

    private void Load_typical() {
        this.state = 0;
        this.depLabel.setText(this.dep_names[getSharedPreferences("depSelected", 0).getInt("depcode", 0)]);
        ViewStub viewStub = this.tablayout;
        if (viewStub == null) {
            this.tablayout = (ViewStub) findViewById(R.id.stub_import);
            this.tablayout.inflate();
        } else {
            viewStub.setVisibility(View.VISIBLE);
        }
        this.tabs = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        this.tabs.removeAllTabs();
        this.tabs.setVisibility(View.VISIBLE);
        TabLayout tabLayout = this.tabs;
        tabLayout.addTab(tabLayout.newTab().setText("Current"));
        tabLayout = this.tabs;
        tabLayout.addTab(tabLayout.newTab().setText("Retired"));
        this.tabs.setTabGravity(0);
        pageAdapter pageadapter = new pageAdapter(getSupportFragmentManager(), this.tabs.getTabCount());
        pageadapter.notifyDataSetChanged();
        viewPager.setAdapter(pageadapter);
        viewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(this.tabs));
        this.tabs.addOnTabSelectedListener(new ViewPagerOnTabSelectedListener(viewPager));
    }

    private void Load_Search(String str) {
        this.state = 2;
        this.depLabel.setText("Search Results");
        ViewStub viewStub = this.tablayout;
        if (viewStub != null) {
            viewStub.setVisibility(View.GONE);
        }
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        this.SearchAdapter = new SearchAdapter(getSupportFragmentManager(), 1, str);
        this.SearchAdapter.notifyDataSetChanged();
        viewPager.setAdapter(this.SearchAdapter);
    }

    private void Load_Favourite() {
        this.state = 1;
        ViewStub viewStub = this.tablayout;
        if (viewStub != null) {
            viewStub.setVisibility(View.GONE);
        }
        this.depLabel.setText("Favourites");
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        FavAdapter favAdapter = new FavAdapter(getSupportFragmentManager(), 1);
        favAdapter.notifyDataSetChanged();
        viewPager.setAdapter(favAdapter);
    }

    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(8388611)) {
            drawerLayout.closeDrawer(8388611);
        } else if (this.state != 0) {
            Load_typical();
        } else {
            super.onBackPressed();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        ((SearchView) menu.findItem(R.id.action_search).getActionView()).setOnQueryTextListener(new OnQueryTextListener() {
            public boolean onQueryTextSubmit(String str) {
                MainActivity.this.Load_Search(str);
                return true;
            }

            public boolean onQueryTextChange(String str) {
                if (str.length() != 0) {
                    MainActivity.this.Load_Search(str);
                } else {
                    MainActivity.this.Load_typical();
                }
                return true;
            }
        });
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.action_about) {
            Builder builder = new Builder(this);
            builder.setTitle("About MACEHUB").setMessage("MACEHUB is an initiative by Students of MACE with a pure motive to bring all the academic and non-academic layers of MACE into a digital umbrella , Thanks for your support :)");
            builder.setPositiveButton("OK", new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            builder.create().show();
            return true;
        } else if (itemId == R.id.action_load) {
            return loadDb();
        } else {
            return super.onOptionsItemSelected(menuItem);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private boolean loadDb() {
        if(isNetworkAvailable()){
            progressDoalog = new ProgressDialog(this);
            progressDoalog.setMax(100);
            progressDoalog.setIndeterminate(true);
            progressDoalog.setMessage("waiting...");
            progressDoalog.setTitle("Hold your breath, it's just a breeze ! (Need Internet)");
            progressDoalog.setProgressStyle(0);
            progressDoalog.setCancelable(false);
            progressDoalog.show();
            present=0;
            count=0;
        try {
            loadNewDB();
            //getWebsite((String) this.urls.get(this.present), (String) this.depcodes.get(this.present));
        } catch (Exception e) {
            e.printStackTrace();
        }
            return true;

        }else{

            Builder builder = new Builder(this);
            builder.setPositiveButton("Retry", new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.this.loadDb();
                }
            });
            builder.setNegativeButton("Cancel", new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.this.Load_typical();
                }
            });
            builder.setMessage("Internet Required for the contact details to be loaded ! retry again!").setTitle("Internet Required ");
            builder.setCancelable(false);
            builder.create().show();
            return false;
        }
    }


    private void msg(String details,String title){
        Builder builder = new Builder(this);
        builder.setPositiveButton("OK", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setMessage(details).setTitle(title);
        builder.setCancelable(false);
        builder.create().show();
    }

    private void loadNewDB(){
        for(int i=0;i<18;i++){
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run(){
                    int retries = 0,maxRetries=100;
                    while(retries < maxRetries)
                    {
                        try
                        {
                            Document doc = Jsoup.connect(urls.get(finalI)).timeout(60* 1000).get();
                            Elements links = doc.select("div[class*=Staff-Holder col-all-12 staff-headl]");
                            retries=maxRetries;
                            snipStaffs(links,depcodes.get(finalI));

                        }
                        catch(final Exception  e)
                        {
                            retries++;
                            if(retries==maxRetries){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDoalog.setMessage("Network Error !");
                                        progressDoalog.dismiss();
                                        Toast.makeText(getApplicationContext()," I cant fetch data right now because of poor network , Try again",Toast.LENGTH_SHORT).show();
                                    }
                                });}
                        }

                    }



                }

            }).start();
        }
    }

    private void snipStaffs(Elements links,final String department){
        final StringBuilder builder = new StringBuilder();
        final ArrayList<staff> staffs = new ArrayList();
        Elements dets;
        Element span;
        Element p;
        String imlocation;
        for (Element link : links) {
            builder.setLength(0);
            staff staff=new staff();
            staff.setDepartment(department);
            dets=link.getElementsByTag("li");
            String name="null";

            for (Element det : dets)
            {   span=det.getElementsByTag("span").first();
                p=det.getElementsByTag("p").first();
                if(span!=null && p!=null) {
                    String spanstring=span.text();
                    final String pstring=p.text();
                    if(spanstring.equalsIgnoreCase("name")){
                        count++;
                        name=pstring;
                        staff.setName(pstring);
                    }else {
                        builder.append(spanstring).append(" : ").append(pstring).append("\n");
                    }
                }
            }
            imlocation=link.getElementsByTag("img").attr("src");
            staff.setImgloc(imlocation);
            staff.setId(getIdFromIMG( name + imlocation.substring(imlocation.length() - 9, imlocation.length()-4)));
            staff.setDetails(builder.toString());
            staffs.add(staff);
            staff=null;

        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDoalog.setMessage(String.valueOf(count)+ " contacts Loaded !");

                addStaffsToDB(staffs);

                staffs.clear();

                if(present<17){
                    present+=1;
                }
                else{
                    progressDoalog.cancel();
                    Load_typical();
                }
            }});



    }



    private void getWebsite(final String str, final String str2) {

        new Thread(new Runnable() {

            @Override

            public void run() {
                final StringBuilder builder = new StringBuilder();
                final ArrayList<staff> staffs = new ArrayList();

                try {

                    Document doc = Jsoup.connect(str).get();
                    Elements links = doc.select("div[class*=Staff-Holder col-all-12 staff-headl]");


                    Elements dets;
                    Element span;
                    Element p;
                    String imlocation;


                    for (Element link : links) {
                        builder.setLength(0);
                        staff staff=new staff();
                        staff.setDepartment(str2);
                        dets=link.getElementsByTag("li");
                        String name="null";

                        for (Element det : dets)
                        {   span=det.getElementsByTag("span").first();
                            p=det.getElementsByTag("p").first();
                            if(span!=null && p!=null) {
                                String spanstring=span.text();
                                final String pstring=p.text();
                                if(spanstring.equalsIgnoreCase("name")){
                                    count++;
                                    name=pstring;
                                    staff.setName(pstring);
                                }else {
                                    builder.append(spanstring).append(" : ").append(pstring).append("\n");
                                }
                            }
                        }

                        imlocation=link.getElementsByTag("img").attr("src");
                        staff.setImgloc(imlocation);
                        staff.setId(getIdFromIMG( name + imlocation.substring(imlocation.length() - 9, imlocation.length()-4)));
                        staff.setDetails(builder.toString());
                        staffs.add(staff);
                        staff=null;
                    }



                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDoalog.setMessage(String.valueOf(count)+ " contacts Loaded !");
                        addStaffsToDB(staffs);
                        staffs.clear();
                        if(present<17){
                            present+=1;
                            getWebsite((String) urls.get(present), (String) depcodes.get(present));
                        }
                        else{
                            progressDoalog.cancel();
                            Load_typical();
                        }
                    }});




                // t.setText(builder.toString());


            }

        }).start();

    }



    public boolean addStaffsToDB(ArrayList<staff> staffsToAdd){
        while(!database_is_free);
        database_is_free=false;
        DatabaseHandler dbhandle = new DatabaseHandler(getApplicationContext());
        int count=staffsToAdd.size();
        for(int i=0;i<count;i++){
            dbhandle.addStaff(staffsToAdd.get(i));
        }
        dbhandle.close();
        database_is_free=true;
        return true;
    }

    private String getIdFromIMG(String str) {
        return String.valueOf(Math.abs(str.hashCode()));
    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        Editor edit = getApplicationContext().getSharedPreferences("depSelected", 0).edit();
        if (itemId == R.id.nav_administration) {
            edit.putInt("depcode", 0);
            edit.commit();
            Load_typical();
        } else if (itemId == R.id.nav_civil) {
            edit.putInt("depcode", 2);
            edit.commit();
            Load_typical();
        } else if (itemId == R.id.nav_cs) {
            edit.putInt("depcode", 10);
            edit.commit();
            Load_typical();
        } else if (itemId == R.id.nav_ec) {
            edit.putInt("depcode", 8);
            edit.commit();
            Load_typical();
        } else if (itemId == R.id.nav_eee) {
            edit.putInt("depcode", 6);
            edit.commit();
            Load_typical();
        } else if (itemId == R.id.nav_maths) {
            edit.putInt("depcode", 14);
            edit.commit();
            Load_typical();
        } else if (itemId == R.id.nav_mca) {
            edit.putInt("depcode", 12);
            edit.commit();
            Load_typical();
        } else if (itemId == R.id.nav_mech) {
            edit.putInt("depcode", 4);
            edit.commit();
            Load_typical();
        } else if (itemId == R.id.nav_science) {
            edit.putInt("depcode", 16);
            edit.commit();
            Load_typical();
        } else if (itemId == R.id.nav_share) {
            Shareme();
        } else if (itemId == R.id.nav_fav) {
            Load_Favourite();
        }
        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(8388611);
        return true;
    }

    private void Shareme() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", "MACEHUB-Faculty");
        intent.putExtra("android.intent.extra.TEXT", "Hey, I have found this usefull APP made in association with our MACEHUB, Containing all the staffs details of Mace, please give it a try !");
        startActivity(Intent.createChooser(intent, "Share via"));
    }
}
