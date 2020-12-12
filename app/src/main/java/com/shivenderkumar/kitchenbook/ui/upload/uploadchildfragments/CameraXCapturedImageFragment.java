package com.shivenderkumar.kitchenbook.ui.upload.uploadchildfragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.ImageProxy;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.shivenderkumar.kitchenbook.R;
import com.shivenderkumar.kitchenbook.ui.upload.uploadchildfragments.image_filter.AdapterFilterList;
import com.shivenderkumar.kitchenbook.ui.upload.uploadchildfragments.image_filter.DataSource;
import com.shivenderkumar.kitchenbook.ui.upload.viewmodelcamerx.ImageProxyViewModel;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class CameraXCapturedImageFragment extends Fragment {

    ImageButton imageButton_close, imageButton_check;
    ImageView imageview_preview;

    TextView button_effects, button_edit;
    private Boolean flag_effect= true; // true-> filter false ->edit
    View include_lab_edit;
    View  include_lab_filter;
    private RecyclerView recyclerView_FilterList;
    private AdapterFilterList adapter_FilterList;

    boolean flag_backpressed = true;

    // fragment communication MutableLivedata
    private ImageProxyViewModel imageProxyViewModel;
    private ImageProxy imageProxy = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("YYYYYYYYYYYYYYY ONCREATEVIEW CAMERAX IMAGE CAPTUREDFRAGMENT CALLED");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera_x_captured_image, container, false);

        init(view);
        setLabVisibilty(true);

        recyclerView_FilterList = view.findViewById(R.id.recyclerView_FilterList);
        DataSource dataSource = new DataSource(getContext());

        adapter_FilterList = new AdapterFilterList(getContext(), dataSource.getArrayListfilterItem());
        recyclerView_FilterList.setItemAnimator(new DefaultItemAnimator());
        recyclerView_FilterList.setAdapter(adapter_FilterList);
        adapter_FilterList.notifyDataSetChanged();

        setOnClickListeners();
        observeImageProxyViewModel();
        changeActivityOnBackPressedFuntionality();

        return view;
    }

    private void init(View root){
        imageButton_check = root.findViewById(R.id.imageview_upload_check);
        imageButton_close = root.findViewById(R.id.imageview_upload_close);
        imageview_preview = root.findViewById(R.id.imageview_preview);

        button_edit = root.findViewById(R.id.button_edit);
        button_effects = root.findViewById(R.id.button_effects);
        include_lab_edit = root.findViewById(R.id.include_lab_edit);
        include_lab_filter = root.findViewById(R.id.include_lab_filter);
    }


    private void setOnClickListeners() {
        imageButton_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftReplaceCameraXF();
            }
        });

        imageButton_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftAddUploadRecipeF();
            }
        });

        button_effects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_effect == true){
                    setLabVisibilty(true);
                }
            }
        });

        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_effect == false){
                    setLabVisibilty(false);
                }
            }
        });

    }

    private void setLabVisibilty(Boolean flag) {
        flag_effect = flag;
        if(flag_effect){
            //change button edit color to grey
            button_edit.setBackgroundColor(getResources().getColor(R.color.grey));
            button_edit.setTypeface(null, Typeface.NORMAL);
            include_lab_edit.setVisibility(View.INVISIBLE);
            // change include layout to editlab


            //visible filter lab
            include_lab_filter.setVisibility(View.VISIBLE);
            button_effects.setBackgroundColor(getResources().getColor(R.color.white));
            button_effects.setTypeface(null, Typeface.BOLD);
            // change include layout to filterlab

            flag_effect = false;
        }
        else if(flag_effect==false){
            //change button edit color to grey
            include_lab_filter.setVisibility(View.INVISIBLE);
            button_effects.setBackgroundColor(getResources().getColor(R.color.grey));
            button_effects.setTypeface(null, Typeface.NORMAL);
            // change include layout to editlab


            //visible filter lab
            include_lab_edit.setVisibility(View.VISIBLE);
            button_edit.setBackgroundColor(getResources().getColor(R.color.white));
            button_edit.setTypeface(null, Typeface.BOLD);
            // change include layout to filterlab
            flag_effect = true;

        }
    }

    private void ftReplaceCameraXF(){
        Fragment fragment_camerax = new CamerXFragment();
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.parent_fragment_container, fragment_camerax,"TAG_FARGMENT_CAMERAX").commit();

    }

    private void ftAddUploadRecipeF() {
        Fragment fragment_uploadRecipe = new UploadRecipeFragment();
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.parent_fragment_container,fragment_uploadRecipe,"TAG_FRAGMENT_UPLOAD_RECIPE").commit();

    }

    private void observeImageProxyViewModel() {
        imageProxyViewModel = new ViewModelProvider(requireParentFragment()).get(ImageProxyViewModel.class);
        imageProxyViewModel.getMutableLiveDataIP().observe(getViewLifecycleOwner(), imageProxy1 -> {
            setImageProxyToImageView(imageProxy1);
        });
    }

    private void setImageProxyToImageView(ImageProxy imageProxy1) {
        imageProxy = imageProxy1;
        setImageView();
    }

    private void setImageView() {
        if(imageProxy != null){
            Bitmap bitmap = imageProxyToBitMap(imageProxy);
            System.out.println("BITMAP BITMAP BITMAP WIDTH : "+bitmap.getWidth()
                    +"BITMAP BITMAP BITMAP HEIGHT : "+bitmap.getHeight());

            imageProxyViewModel.setImage_bitmap(bitmap);
            imageview_preview.setImageBitmap(bitmap);
        }
        else {
            return;
        }
    }

    private Bitmap imageProxyToBitMap(ImageProxy imageProxy1) {
        ByteBuffer buffer = imageProxy1.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length,null);
    }

    void changeActivityOnBackPressedFuntionality(){
        //implement on back pressed for fragment
        getActivity().getOnBackPressedDispatcher().addCallback((LifecycleOwner) this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if( flag_backpressed == true){   // backpressed callback flag true -> change backpressed to back to cameraxpreview fragment
                    // in here you can do logic when backPress is clicked
                    ftReplaceCameraXF();
                }
                else{  // backpressed callback flag false -> change backpressed to back to activity normal behaviour backpressed
                    flag_backpressed = false;
                    ftReplaceCameraXF();
                }
            }
        });
    }




















    ////////////////////////////////////////

    @Override
    public void onPause() {
        System.out.println("YYYYYYYYYYYYYYY ON PAUSE CAMERAX IMAGE CAPTUREDFRAGMENT CALLED");
        super.onPause();
    }


    @Override
    public void onResume() {
        System.out.println("YYYYYYYYYYYYYYY ON RESUME CAMERAX IMAGE CAPTUREDFRAGMENT CALLED");
        super.onResume();
    }

    @Override
    public void onStart() {
        System.out.println("YYYYYYYYYYYYYYY ON START CAMERAX IMAGE CAPTUREDFRAGMENT CALLED");

        super.onStart();
    }

    @Override
    public void onStop() {
        System.out.println("YYYYYYYYYYYYYYY ON STOP CAMERAX IMAGE CAPTUREDFRAGMENT CALLED");

        super.onStop();
    }

    @Override
    public void onDestroyView() {
        System.out.println("YYYYYYYYYYYYYYY ON DESTROYVIEW CAMERAX IMAGE CAPTUREDFRAGMENT CALLED");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        System.out.println("YYYYYYYYYYYYYYY ON DESTROY CAMERAX IMAGE CAPTUREDFRAGMENT CALLED");
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        System.out.println("YYYYYYYYYYYYYYY ON CREATE CAMERAX IMAGE CAPTUREDFRAGMENT CALLED");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        System.out.println("YYYYYYYYYYYYYYY ON ATTACH CAMERAX IMAGE CAPTUREDFRAGMENT CALLED");
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        System.out.println("YYYYYYYYYYYYYYY ON DEATCH CAMERAX IMAGE CAPTUREDFRAGMENT CALLED");
        super.onDetach();
    }

}



//    private Bitmap cropSqaureBitMap(Bitmap bitmap) {
        //  int activityWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        //  int activityHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();


        //convert dp to pixels
        //  activityWidth = (int) ((activityWidth) * getContext().getResources().getDisplayMetrics().density);
        //  activityHeight = (int) ((activityHeight) * getContext().getResources().getDisplayMetrics().density);

        //  activityWidth = activityWidth / 2;
        //  activityHeight = activityHeight / 2;

//        System.out.println("SQAURE WWWWWWWWWWWWWWWWWWWWWWWWIDTH + 24 in pixels : "+ sqaureWidth);

//        int startX = bitmap.getWidth()/2 - activityWidth;
//        int startY = bitmap.getHeight()/2 - activityHeight;
//
//        int endX = bitmap.getWidth()/2 + activityWidth;
//        int endY = bitmap.getHeight()/2 + activityHeight;
//
//        System.out.println("BITMAP BITMAP START X :"+startX+" START Y :"+startY+" // END X :"+endX+" END Y :"+endY);

//        int bW = bitmap.getWidth();
//
//        return Bitmap.createBitmap(bitmap, 0, 0, bW, bW);
//
//    }