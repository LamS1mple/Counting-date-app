package com.example.countingdate.FragmentBottom;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countingdate.MainActivity;
import com.example.countingdate.Model.User;
import com.example.countingdate.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class FragmentBottomOne extends Fragment {
    ImageView img1User, img2User, imgIcon;
    TextView changBackgroundUser, changName, changBirthday, changeSex, changeMask, changeColorSex;
    TextView name1, sex1, date1;
    TextView name2, sex2, date2;

    private EditText inputTextDialog;
    private Button btnAccept, btnCancel;
    ArrayList<Integer> listMask;
    private boolean selectedUser;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private User mUser1, mUser2;


    private ActivityResultLauncher<String> lauch = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri o) {
                    UCrop(o, o);
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_one, container, false);
        addMask();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getPreferences(getActivity().MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
        String user1 = sharedPreferences.getString("user1", "");
        String user2 = sharedPreferences.getString("user2", "");

        if (!user1.equals("")) {
            mUser1 = gson.fromJson(user1, User.class);
        } else {
            Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + getActivity().getPackageName() +
                    "/drawable/" + getResources().getResourceEntryName(R.drawable.avatar1));
            mUser1 = new User("nickname1", true
                    , uri.toString(), R.drawable.mask3, Calendar.getInstance());
        }

        if (!user2.equals("")) {
            mUser2 = gson.fromJson(user2, User.class);
        } else {
            Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + getActivity().getPackageName() +
                    "/drawable/" + getResources().getResourceEntryName(R.drawable.avatar2));

            mUser2 = new User("nickname1", false
                    , uri.toString(), R.drawable.mask3, Calendar.getInstance());
        }

        img1User = view.findViewById(R.id.img1);
        img2User = view.findViewById(R.id.img2);
        imgIcon = view.findViewById(R.id.icon_ani);
        name1 = view.findViewById(R.id.name1);
        name2 = view.findViewById(R.id.name2);


        setFrameImg(img1User, mUser1);
        setFrameImg(img2User, mUser2);
        name1.setText(mUser1.getName());
        name2.setText(mUser2.getName());

        img1User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedUser = true;
                createdDialog();
            }
        });

        img2User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedUser = false;
                createdDialog();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.boder_image);
        imgIcon.startAnimation(animation);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            Log.d("hello", "in");
            Uri resultUri = UCrop.getOutput(data);


            if (selectedUser) {
                mUser1.setImgUser(resultUri.toString());
                editor.putString("user1", gson.toJson(mUser1));
                setFrameImg(img1User, mUser1);
            } else {
                mUser2.setImgUser(resultUri.toString());
                editor.putString("user2", gson.toJson(mUser2));
                setFrameImg(img2User, mUser2);
            }
            editor.commit();

        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);

        }
    }

    public Bitmap convertUriToBitmap(Uri i) {
        ContentResolver contentResolver = getActivity().getContentResolver();

        InputStream inputStream = null;
        try {
            inputStream = contentResolver.openInputStream(i);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);

        return imageBitmap;

    }

    public void UCrop(Uri sourceUri, Uri destinationUri) {
        String chlid = UUID.randomUUID() + ".jpg";
        UCrop.of(sourceUri, Uri.fromFile(new File(getActivity().getCacheDir(), chlid)))
                .withAspectRatio(1, 1)
                .start(getActivity().getApplicationContext(), this);
    }

    public void addMask() {
        listMask = new ArrayList<>();
        listMask.add(R.drawable.mask1);
        listMask.add(R.drawable.mask2);
        listMask.add(R.drawable.mask3);
        listMask.add(R.drawable.mask4);
        listMask.add(R.drawable.mask5);
        listMask.add(R.drawable.mask6);
        listMask.add(R.drawable.mask7);

    }

    public void createdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_bottom, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

        mapDialog(view);
        changeMask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogBottomSheet();
                dialog.dismiss();
            }
        });

        changBackgroundUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lauch.launch("image/*");
                dialog.dismiss();
            }
        });

        changName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogInput();
                dialog.dismiss();
            }
        });

    }

    public void createDialogInput(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.dialog_input,null, false);
        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


        inputTextDialog = view.findViewById(R.id.editText);
        btnAccept = view.findViewById(R.id.accept);
        btnCancel = view.findViewById(R.id.cancel_dialog_input);
        if(selectedUser){
            inputTextDialog.setText(mUser1.getName());
        }
        else{
            inputTextDialog.setText(mUser2.getName());
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEdit = inputTextDialog.getText().toString();
                if(selectedUser){
                    mUser1.setName(textEdit);
                    editor.putString("user1", gson.toJson(mUser1));
                    name1.setText(textEdit);
                }
                else{
                    mUser2.setName(textEdit);
                    editor.putString("user2", gson.toJson(mUser2));
                    name2.setText(textEdit);
                }
                alertDialog.dismiss();

                editor.commit();
            }

        });

    }

    private void mapDialog(View view) {
        changBackgroundUser = view.findViewById(R.id.change_background_user);
        changName = view.findViewById(R.id.chang_name);
        changBirthday = view.findViewById(R.id.change_birthday);
        changeSex = view.findViewById(R.id.change_sex);
        changeMask = view.findViewById(R.id.change_mask);
        changeColorSex = view.findViewById(R.id.change_color_background_sex);
    }

    public void createDialogBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_bottom_sheet, null);
        bottomSheetDialog.setContentView(view);

        bottomSheetDialog.show();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_dialog_bottom_sheet);
        MaskRecycler maskRecycler = new MaskRecycler();

        maskRecycler.setSendToFragment(new MaskRecycler.SendToFragment() {
            @Override
            public void sendImg(int imgInt) {
                if (selectedUser) {
                    mUser1.setMask(imgInt);
                    editor.putString("user1", gson.toJson(mUser1));

                    setFrameImg(img1User, mUser1);
                } else {
                    mUser2.setMask(imgInt);
                    editor.putString("user2", gson.toJson(mUser2));

                    setFrameImg(img2User, mUser2);
                }
                editor.commit();
                bottomSheetDialog.cancel();
            }
        });

        maskRecycler.setListMask(listMask);
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(requireContext(), 4, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(maskRecycler);

    }

    public void setFrameImg(ImageView imageView, User user) {
        Bitmap imgUser, maskUser, result;

        maskUser = BitmapFactory.decodeResource(getResources(), user.getMask());
        imgUser = convertUriToBitmap(user.getUri());
        try {
            if (imgUser != null) {
                int w = imgUser.getWidth();
                int h = imgUser.getWidth();
                result = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                maskUser = Bitmap.createScaledBitmap(maskUser, w, h, true);
                Canvas canvas = new Canvas(result);
                canvas.drawBitmap(imgUser, 0, 0, null);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
                canvas.drawBitmap(maskUser, 0, 0, paint);
//                paint.setStyle(Paint.Style.STROKE);
//                paint.setColor(Color.WHITE);

//                canvas.drawARGB(1, 0 , 0, 0 );

            } else {
                result = null;
            }
            imageView.setImageBitmap(result);

        } catch (Exception e) {
            Log.d("loi", e.toString());
        }

    }
}
