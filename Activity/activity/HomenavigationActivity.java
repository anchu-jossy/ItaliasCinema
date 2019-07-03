package com.ItaliasCinemas.ajit.Italiascinema.Activity.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.ItaliaApi;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.RetrofitClientInstance;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.adapter.MyPagerAdapter;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.adapter.SearchAdapter;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.fragments.AboutUsFragment;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.fragments.FavouritesFragment;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.interfaces.CommonInterface;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.FeatureMoviesResponse;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.Info;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.savedata.SaveDataClass;
import com.ItaliasCinemas.ajit.Italiascinema.R;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomenavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CommonInterface {
    public static Object get;
    MenuItem menuSearch, menuBell, menuApp;
    Boolean isSearchClicked = false;
    int count;
    @BindView(R.id.container)
    CoordinatorLayout container;
    @BindView(R.id.home_container)
    ConstraintLayout homeContainer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pager_header)
    PagerTabStrip pagerHeader;
    @BindView(R.id.vpPager)
    ViewPager vpPager;
    /*@BindView(R.id.pager_header_search)
    PagerTabStrip pagerHeaderSearch;
    @BindView(R.id.vpPagerSearch)
    ViewPager vpPagerSearch;*/
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    MyPagerAdapter adapterViewPager;
    SaveDataClass saveDataClass;
    String notificationCount;
    @BindView(R.id.search_recyclerview)
    RecyclerView searchRecyclerview;

    ArrayList<Info> infoArrayList = new ArrayList<>();
    @BindView(R.id.tv_recent)
    TextView tvRecent;
    @BindView(R.id.search_container)
    RelativeLayout searchContainer;
    SearchAdapter searchAdapter;
    FeatureMoviesResponse featureMoviesResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homenavigation);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        settingDrawerLayout();
        settingNavigationView();
        setttingPagerHeaderForSearch();
        settingPagerHeader();

        Log.d(" SaveDataClass.", SaveDataClass.getEmailId(HomenavigationActivity.this));
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void settingDrawerLayout() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    private String getNotificationCount(NotificationBadge notificationBadge) {

        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);

        Call<FeatureMoviesResponse> call = italiaApi.getNotification(SaveDataClass.getUserID(HomenavigationActivity.this));

        call.enqueue(new Callback<FeatureMoviesResponse>() {
            @Override
            public void onResponse(Call<FeatureMoviesResponse> call, Response<FeatureMoviesResponse> response) {


                featureMoviesResponse = response.body();

                if (featureMoviesResponse.getStatus() == 1) {

                    SaveDataClass.getInstance().setNotificationcount(featureMoviesResponse.getCount());
                    notificationCount = featureMoviesResponse.getCount();
                    Log.d("Activity123", featureMoviesResponse.getCount() + "");
                    notificationBadge.setNumber(Integer.parseInt(featureMoviesResponse.getCount()));
                }

            }

            @Override
            public void onFailure(Call<FeatureMoviesResponse> call, Throwable t) {

            }
        });
        return notificationCount;
    }

    private void settingNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);

        View hView = navigationView.getHeaderView(0);
        TextView nav_user = (TextView) hView.findViewById(R.id.tv_profile_name);
        nav_user.setText(SaveDataClass.getUserName(HomenavigationActivity.this));
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setttingPagerHeaderForSearch() {
       /* pagerHeaderSearch.setBackgroundColor(Color.BLACK);
        pagerHeaderSearch.setDrawFullUnderline(false);
        pagerHeaderSearch.setTextSpacing(0);
        pagerHeaderSearch.setTabIndicatorColor(Color.RED);
        pagerHeaderSearch.setTextColor(Color.WHITE);
        pagerHeaderSearch.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        pagerHeaderSearch.setDrawFullUnderline(false);
        SearchPagerAdapter searchPagerAdapter = new SearchPagerAdapter(getSupportFragmentManager());
        vpPagerSearch.setAdapter(searchPagerAdapter);*/

        getSearchData();
    }

    private void getSearchData() {

        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);

        Call<FeatureMoviesResponse> call = italiaApi.getSearchData("53");

        call.enqueue(new Callback<FeatureMoviesResponse>() {
            @Override
            public void onResponse(Call<FeatureMoviesResponse> call, Response<FeatureMoviesResponse> response) {


                FeatureMoviesResponse featureMoviesResponse = response.body();

                if (featureMoviesResponse.getStatus() == 1) {

                    infoArrayList.clear();
                    for (int i = 0; i < featureMoviesResponse.getInfo().size(); i++) {
                        infoArrayList.add(featureMoviesResponse.getInfo().get(i));
                    }

                    searchRecyclerview.setLayoutManager(new LinearLayoutManager(HomenavigationActivity.this));
                    searchAdapter = new SearchAdapter(HomenavigationActivity.this, infoArrayList);
                    searchRecyclerview.setAdapter(searchAdapter);
                }

            }

            @Override
            public void onFailure(Call<FeatureMoviesResponse> call, Throwable t) {
                Log.d("Fetaure1", t.getMessage());
            }
        });

    }

    private void settingPagerHeader() {

        pagerHeader.setBackgroundColor(Color.BLACK);
        pagerHeader.setDrawFullUnderline(false);
        pagerHeader.setTextSpacing(0);
        pagerHeader.setTabIndicatorColor(Color.RED);
        pagerHeader.setTextColor(Color.RED);
        pagerHeader.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        pagerHeader.setDrawFullUnderline(false);
        pagerHeader.setTextColor(Color.WHITE);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), HomenavigationActivity.this);
        vpPager.setAdapter(adapterViewPager);
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                //   Log.d("onPageScrolled", i1 + "");
            }

            @Override
            public void onPageSelected(int i) {
                Log.d("onPageSelected", adapterViewPager.getPageTitle(i) + "");


            }

            @Override
            public void onPageScrollStateChanged(int i) {

                Log.d("onPageScrollStanged", i + "");
            }
        });
    }

    public void onbackpressOfFavouritesActivity() {
        startActivity(new Intent(HomenavigationActivity.this, HomenavigationActivity.class));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {


            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.findFragmentById(R.id.container);

            Log.d("onBackPressed", fragmentManager.findFragmentById(R.id.container) + "");
          /*  Fragment fragmentInFrame = (Fragment)getSupportFragmentManager()
                    .findFragmentById(R.id.frag2_view)
                    .findFragmentById(R.id.frag2_view);*/
        }

    }

    /*  @Override
       public void  backPressFavourite(){

          startActivity(new Intent(this,HomenavigationActivity.class));
       }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.homenavigation, menu);

        settingBellIcon(menu);
        settingAppandSearchIcon(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId(); // Retrieve the id of the selected menu item

        switch (id) {
            case R.id.action_bell: // "@+id/about" is the id of your menu item (menu layout)
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.action_applogo: // "@+id/about" is the id of your menu item (menu layout)
                startActivity(new Intent(this, HomenavigationActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void settingAppandSearchIcon(Menu menu) {
        menuApp = menu.findItem(R.id.action_applogo);
        menuSearch = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuSearch.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                    @Override
                    public boolean onClose() {
                        Log.d("count1234", count + "");
                        menuBell.setVisible(true);
                        menuApp.setVisible(true);
                        count++;
                        return false;
                    }
                });
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeContainer.setVisibility(View.GONE);
                searchContainer.setVisibility(View.VISIBLE);
                menuBell.setVisible(false);
                menuApp.setVisible(false);


            }
        });


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                homeContainer.setVisibility(View.VISIBLE);
                searchContainer.setVisibility(View.GONE);
                menuBell.setVisible(true);
                menuApp.setVisible(true);
                count++;
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                Log.d("count123", s + "");
                filter(s.toString());
                return false;
            }
        });
    }

    void filter(String text) {
        ArrayList<Info> temp = new ArrayList();
        if (infoArrayList != null) {


            for (Info d : infoArrayList) {
                //or use .equal(text) with you want equal match
                //use .toLowerCase() for better matches

                if (d.getMovieName().contains(text) || d.getStarCast().contains(text)) {
                    temp.add(d);
                }
            }
            //update recyclerview

            searchAdapter.updateList(temp);

        }
    }

    private void settingBellIcon(Menu menu) {
        Log.d("Activity123", "settingbell");
        menuBell = menu.findItem(
                R.id.action_bell);

        menuBell.setActionView(R.layout.badge_layout);

        View view = menuBell.getActionView();
        NotificationBadge notificationBadge = view.findViewById(R.id.badge);

        getNotificationCount(notificationBadge);
        Log.d("Singletonnotification" +
                "", notificationCount + "" + SaveDataClass.getInstance().getNotificationcount() + "    ");
        notificationBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomenavigationActivity.this, NotificationActivity.class));
            }
        });


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_about_us) {
            AboutUsFragment aboutUsFragment = new AboutUsFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, aboutUsFragment);
            transaction.commit();


        } else if (id == R.id.nav_settings) {

            startActivity(new Intent(HomenavigationActivity.this, SettingsActivity.class));
        } else if (id == R.id.nav_logout) {
            String username = "";
            String password = "";
            SaveDataClass.setUserName(HomenavigationActivity.this, "");
            SaveDataClass.setUserPassword(HomenavigationActivity.this, "");
            finish();
            SaveDataClass.getInstance().setIslogout("HomeActivity");
            startActivity(new Intent(HomenavigationActivity.this, RegistrationActivity.class));
        } else if (id == R.id.nav_favourites) {

            FavouritesFragment favouritesFragment = new FavouritesFragment();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.addToBackStack("FavouriteFragment");
            fragmentTransaction.replace(R.id.container, favouritesFragment);
            fragmentTransaction.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
