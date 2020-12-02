package com.shivenderkumar.kitchenbook.ui.upload.uploadchildfragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.ImageProxy;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.shivenderkumar.kitchenbook.R;
import com.shivenderkumar.kitchenbook.model.Recipe;
import com.shivenderkumar.kitchenbook.ui.upload.viewmodelcamerx.ImageProxyViewModel;

import org.w3c.dom.EntityReference;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UploadRecipeFragment extends Fragment {
    boolean flag_backpressed = true;
    ImageButton imageButton;
    ImageView imageView_upload;
    Button button_upload;

    TextView textView_recipename, textView_about, textView_ingredients, textView_steps ,textView_tags, textView_TimeToCook;
    EditText editText_recipename, editText_about, editText_ingredients, editText_steps, editText_tags, editText_TimeToCook;

    private ImageProxyViewModel imageProxyViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_upload_recipe, container, false);

        changeActivityOnBackPressedFuntionality();
        init(root);
        setOnclickListerner();
        observeImageProxyViewModel();

        return root;
    }

    void changeActivityOnBackPressedFuntionality(){
        //implement on back pressed for fragment
        getActivity().getOnBackPressedDispatcher().addCallback((LifecycleOwner) this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if( flag_backpressed == true){   // backpressed callback flag true -> change backpressed to back to camerax_captured_image fragment
                    // in here you can do logic when backPress is clicked
                    //flag_backpressed = false;
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.remove(getParentFragmentManager().findFragmentByTag("TAG_FRAGMENT_UPLOAD_RECIPE")).commit();

                }
                else{  // backpressed callback flag false -> change backpressed to back to activity normal behaviour backpressed
                    flag_backpressed = false;
                    getActivity().onBackPressed();
                }
            }
        });
    }

    void init(View root){
        imageButton = root.findViewById(R.id.imagebutton_back);
        button_upload = root.findViewById(R.id.button_upload);
        imageView_upload = root.findViewById(R.id.imageview_upload);

        //init recipe fields
        textView_recipename = root.findViewById(R.id.textview_recipename);
        textView_about = root.findViewById(R.id.textview_about);
        textView_ingredients = root.findViewById(R.id.textview_Ingredients);
        textView_steps = root.findViewById(R.id.textview_Steps);
        textView_tags = root.findViewById(R.id.textview_Tags);

        editText_recipename = root.findViewById(R.id.edittext_recipename);
        editText_about = root.findViewById(R.id.edittext_about);
        editText_ingredients = root.findViewById(R.id.edittext_Ingredients);
        editText_steps = root.findViewById(R.id.edittext_Steps);
        editText_tags = root.findViewById(R.id.edittext_Tags);
        editText_TimeToCook = root.findViewById(R.id.edittext_TimeToCook);

    }

    void setOnclickListerner(){
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        button_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create recipe object
                Recipe recipe = createRecipeObject();
                System.out.println("DDDDDDDDDDDDDDD  createRecipeObject Recipe");

                if(recipe != null){
                //back to cameraX -> remove upload_fragment and remove cameraX_capturedImageFragment
                ftRemoveCameraXImageCapturedRemoveUploadRecipeFAddCameraXF();
                //upload Fragment will make Netwrok Call Firebase and upload recipe data
                }
                else {
                    return;
                }
            }
        });
    }

    private Recipe createRecipeObject() {
        Recipe recipe;
        //get hashmapped Data
        HashMap<String, String> hsmp = getHashMapText();

        if(hsmp.size() > 0){
            for(Map.Entry<String, String> entry : hsmp.entrySet()){
                System.out.println("DDDDDDDDDDDDDDD HASHMAP KEY : " +entry.getKey()+" Value : "+ entry.getValue());
            }

        }
        else {
            recipe = null;
        }

        recipe = null;
        return recipe;
    }

    private HashMap<String, String> getHashMapText() {

        //get data
        HashMap<String, String> hsmp = new HashMap<>();

        String name = editText_recipename.getText().toString();
        hsmp.put("name",name);
        String about = editText_about.getText().toString();
        hsmp.put("about",about);
        String ingredients = editText_ingredients.getText().toString();
        hsmp.put("about",about);
        String steps = editText_steps.getText().toString();
        hsmp.put("steps",steps);
        String tags = editText_tags.getText().toString();
        hsmp.put("tags",tags);
        String timeToCook = editText_TimeToCook.getText().toString();
        hsmp.put("TimeToCook",timeToCook);

        //check data validations
        if(validateData(hsmp)){

        }else {
            hsmp.clear();
        }
        return hsmp;
    }

    private boolean validateData(HashMap<String, String> hsmp) {
        //check null or empty values
        for(Map.Entry<String, String> entry : hsmp.entrySet()){
            if(entry.getValue().isEmpty() || entry.getValue().equals(null)){
                System.out.println("GGGGGGGGGGGGGGGGG VALIDATE Entry Key :"+entry.getKey()+" is invalid");
                return false;
            }
            System.out.println("GGGGGGGGGGGGGGGGG VALIDATE KEY : "+entry.getKey()+" VALUE : "+entry.getValue()+" is valid");
        }




        return true;
    }

    private void ftRemoveCameraXImageCapturedRemoveUploadRecipeFAddCameraXF(){
        Fragment fragment_camerax = new CamerXFragment();
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction
                .remove(getParentFragmentManager().findFragmentByTag("TAG_FRAGMENT_CAMERAX_IMAGECAPTURED"))
                .remove(getParentFragmentManager().findFragmentByTag("TAG_FRAGMENT_UPLOAD_RECIPE"))
                .add(R.id.parent_fragment_container,fragment_camerax,"TAG_FARGMENT_CAMERAX").commit();
    }

    private void observeImageProxyViewModel() {
        imageProxyViewModel = new ViewModelProvider(requireParentFragment()).get(ImageProxyViewModel.class);
        imageProxyViewModel.getMutableLiveDataIP().observe(getViewLifecycleOwner(), imageProxy1 -> {
            // get latest image captured by cameraX fragment
            System.out.println("XXXXXXXXXXXXXXX CAMERAX IMAGE CAPTUREDFRAGMENT NEW IMAGE PROXY CAPTURED IS OBSERVED IMAGEPROXY : "+ imageProxy1.toString()+" //// MLD "+imageProxyViewModel.getMutableLiveDataIP().toString());
            setImageToImageView();
        });
    }

    private void setImageToImageView() {
        imageView_upload.setImageBitmap(imageProxyViewModel.getImage_bitmap());
    }


























    @Override
    public void onPause() {
        System.out.println("XXXXXXXXXXXXXXX ON PAUSE UPLOAD RECIPE FRAGMENT CALLED");
        super.onPause();
    }


    @Override
    public void onResume() {
        System.out.println("XXXXXXXXXXXXXXX ON RESUME UPLOAD RECIPE FRAGMENT CALLED");
        super.onResume();
    }

    @Override
    public void onStart() {
        System.out.println("XXXXXXXXXXXXXXX ON START UPLOAD RECIPE FRAGMENT CALLED");

        super.onStart();
    }

    @Override
    public void onStop() {
        System.out.println("XXXXXXXXXXXXXXX ON STOP UPLOAD RECIPE FRAGMENT CALLED");

        super.onStop();
    }

    @Override
    public void onDestroyView() {
        System.out.println("XXXXXXXXXXXXXXX ON DESTROYVIEW UPLOAD RECIPE FRAGMENT CALLED");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        System.out.println("XXXXXXXXXXXXXXX ON DESTROY UPLOAD RECIPE FRAGMENT CALLED");
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        System.out.println("XXXXXXXXXXXXXXX ON CREATE UPLOAD RECIPE FRAGMENT CALLED");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        System.out.println("XXXXXXXXXXXXXXX ON ATTACH UPLOAD RECIPE FRAGMENT CALLED");
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        System.out.println("XXXXXXXXXXXXXXX ON DEATCH UPLOAD RECIPE FRAGMENT CALLED");
        super.onDetach();
    }


}