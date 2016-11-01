package com.example.administrator.baidumusic.mine.localmusic;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseFragment;
import com.example.administrator.baidumusic.mine.localmusic.album.AlbumFragent;
import com.example.administrator.baidumusic.mine.localmusic.artist.ArtistFragment;
import com.example.administrator.baidumusic.mine.localmusic.folder.FolderFragment;
import com.example.administrator.baidumusic.mine.localmusic.song.SongFragment;

import java.util.ArrayList;

/**
 * Created by dllo on 16/10/31.
 */
public class LocalMusicFragment extends BaseFragment implements View.OnClickListener{
    private TextView back;
    private ImageView search;
    private ImageView scan;
    private ImageView sort;
    private EditText searchbar;
    private TextView cancel;
    private ViewPager vp;
    private TabLayout tab;
    private LinearLayout searchBefore;
    private LinearLayout searchAfter;
    private SongFragment song;

    @Override
    protected int getLayout() {
        return R.layout.frag_local_music;
    }

    @Override
    protected void initView() {
        back = bindView(R.id.back_local_music);
        search = bindView(R.id.search_local_music);
        scan = bindView(R.id.scan_local_music);
        sort = bindView(R.id.sort_local_music);
        searchbar = bindView(R.id.searchbar);
        cancel = bindView(R.id.cancel_local_music);
        vp = bindView(R.id.vp_local_music);
        tab = bindView(R.id.tab_local_music);
        searchBefore = bindView(R.id.before_serarch);
        searchAfter = bindView(R.id.after_search);

        back.setOnClickListener(this);
        search.setOnClickListener(this);
        scan.setOnClickListener(this);
        cancel.setOnClickListener(this);
        sort.setOnClickListener(this);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                searchAfter.setVisibility(View.INVISIBLE);
                searchBefore.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                song.search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void initData() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        ArrayList<Fragment> fragments = new ArrayList<>();
        song = new SongFragment();

        fragments.add(song);
        fragments.add(new FolderFragment());
        fragments.add(new ArtistFragment());
        fragments.add(new AlbumFragent());

        LocalMusicAdapter adapter = new LocalMusicAdapter(getChildFragmentManager());
        adapter.setFragments(fragments);
        tab.setupWithViewPager(vp);
        vp.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 返回
            case R.id.back_local_music:
                getActivity().onBackPressed();
                break;
            // 搜索
            case R.id.search_local_music:

                searchBefore.setVisibility(View.INVISIBLE);
                searchAfter.setVisibility(View.VISIBLE);
                break;
            // 扫描
            case R.id.scan_local_music:

                break;
            // 取消
            case R.id.cancel_local_music:
                searchAfter.setVisibility(View.INVISIBLE);
                searchBefore.setVisibility(View.VISIBLE);
                searchbar.setText(null);
                song.search(null);
                break;
        }
    }


}
