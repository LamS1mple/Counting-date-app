package com.example.countingdate;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import androidx.viewpager2.widget.ViewPager2;

import com.example.countingdate.FragmentAbove.AdapterViewPage2InFragment;
import com.example.countingdate.FragmentBottom.MaskRecycler;
import com.example.countingdate.Model.User;
import com.example.countingdate.Model.Zodiac;
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog;
import com.github.dhaval2404.colorpicker.listener.ColorListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class FragmentAcTwo extends Fragment {

    private AdapterViewPage2InFragment mAdapterViewPage2InFragment;

    private ViewPager2 viewPager2InFragemt;

    private SpringDotsIndicator springDotsIndicator;
    private MainActivity mainActivity;

    private List<Zodiac> zodiacs;


    ImageView img1User, img2User, imgIcon, imgSexUser1, imgSexUser2, imgSignUser1, imgSignUser2;
    TextView changBackgroundUser, changName, changBirthday, changeSex, changeMask, changeColorSex;
    TextView name1, ageUser1, singUser1;
    TextView name2, ageUser2, singUser2;
    TextView changeColorZo;


    private EditText inputTextDialog;
    private Button btnAccept, btnCancel;
    int[] age1, age2;

    SendDataFragmentOneActi sendDataFragmentOneActi;
    ArrayList<Integer> listMask;
    private boolean selectedUser;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private User mUser1, mUser2;
    private View mainView;


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
        View view = inflater.inflate(R.layout.fragment_ac_two, container, false);
        addMask();
        mainView = view;
        addZodiac();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapterViewPage2InFragment = new AdapterViewPage2InFragment(getActivity());
        viewPager2InFragemt = view.findViewById(R.id.view_page2_fra);
        springDotsIndicator = view.findViewById(R.id.dots_indicator);
        mainActivity = (MainActivity) getActivity();
        viewPager2InFragemt.setAdapter(mAdapterViewPage2InFragment);
        springDotsIndicator.attachTo(viewPager2InFragemt);
        sendDataFragmentOneActi = (SendDataFragmentOneActi) getActivity();
        slideFragment();

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
                    , uri.toString(), R.drawable.mask3, Calendar.getInstance()
                    , R.color.color_user1,R.color.color_user1);
        }

        if (!user2.equals("")) {
            mUser2 = gson.fromJson(user2, User.class);
        } else {
            Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + getActivity().getPackageName() +
                    "/drawable/" + getResources().getResourceEntryName(R.drawable.avatar2));

            mUser2 = new User("nickname1", false
                    , uri.toString(), R.drawable.mask3, Calendar.getInstance()
                    , R.color.color_user1,R.color.color_user1);

        }
        age1 = sendDataFragmentOneActi.getFragmentTime(mUser1.getDate());
        age2 = sendDataFragmentOneActi.getFragmentTime(mUser2.getDate());


        img1User = view.findViewById(R.id.img1);
        img2User = view.findViewById(R.id.img2);
        imgIcon = view.findViewById(R.id.icon_ani);
        name1 = view.findViewById(R.id.name1);
        name2 = view.findViewById(R.id.name2);
        imgSexUser1 = view.findViewById(R.id.sex_user1);
        imgSexUser2 = view.findViewById(R.id.sex_user2);
        ageUser1 = view.findViewById(R.id.age_user1);
        ageUser2 = view.findViewById(R.id.age_user2);
        singUser1 = view.findViewById(R.id.sign_name_user1);
        singUser2 = view.findViewById(R.id.sign_name_user2);
        imgSignUser1 = view.findViewById(R.id.sign_img_user1);
        imgSignUser2 = view.findViewById(R.id.sign_img_user2);

        setFrameImg(img1User, mUser1);
        setFrameImg(img2User, mUser2);
        name1.setText(mUser1.getName());
        name2.setText(mUser2.getName());
        setUpAge(ageUser1, age1[0]);
        setUpAge(ageUser2, age2[0]);

        setUpZodiac(imgSignUser1, singUser1
                , zodiacs.get(checkZodiac(mUser1.getDate().get(Calendar.MONTH) + 1
                        , mUser1.getDate().get(Calendar.DAY_OF_MONTH) )));
        setUpZodiac(imgSignUser2, singUser2
                , zodiacs.get(checkZodiac(mUser2.getDate().get(Calendar.MONTH) + 1
                        , mUser2.getDate().get(Calendar.DAY_OF_MONTH) )));


        view.findViewById(R.id.age_background_user1)
                .getBackground().setColorFilter(mUser1.getColorAge(), PorterDuff.Mode.SRC_IN);
        view.findViewById(R.id.zo_background_color1)
                .getBackground().setColorFilter(mUser1.getColorZo(), PorterDuff.Mode.SRC_IN);

        view.findViewById(R.id.age_background_user2)
                .getBackground().setColorFilter(mUser2.getColorAge(), PorterDuff.Mode.SRC_IN);
        view.findViewById(R.id.zo_background_color_2)
                .getBackground().setColorFilter(mUser2.getColorZo(), PorterDuff.Mode.SRC_IN);

        changSexImg(mUser1, imgSexUser1);
        changSexImg(mUser2, imgSexUser2);

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

    void setUpAge(TextView textView, int year) {
        textView.setText(year + "");
    }

    private void slideFragment() {
        viewPager2InFragemt.getChildAt(0).setOnTouchListener(new View.OnTouchListener() {
            float downX = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (event.getX() != 0.0) {
                            downX = event.getX();
                        }
                        mainActivity.setEnableViewPager2(false);

                        break;
                    case MotionEvent.ACTION_UP:
                        mainActivity.setEnableViewPager2(true);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (viewPager2InFragemt.getCurrentItem() == 2 && downX - event.getX() > 0) {
                            mainActivity.setEnableViewPager2(true);
                        } else {
                            if (viewPager2InFragemt.getCurrentItem() == 0 && downX - event.getX() < 0) {
                                mainActivity.setEnableViewPager2(true);
                            }
                        }
                }

                return false;
            }
        });
    }

    public void pickerColor(View view, boolean check){
        new MaterialColorPickerDialog
                .Builder(mainActivity)
                .setTitle(R.string.choose_color)
                .setColors(getResources().getStringArray(R.array.themeColors))
                .setColorListener(new ColorListener() {
                    @Override
                    public void onColorSelected(int i, @NonNull String s) {
                        view.getBackground().setColorFilter(i, PorterDuff.Mode.SRC_IN);
                        if (selectedUser){
                            checkColor(check, mUser1, i);
                            editor.putString("user1", gson.toJson(mUser1));
                        }
                        else{
                            checkColor(check, mUser2, i);
                            editor.putString("user2", gson.toJson(mUser2));
                        }

                        editor.commit();
                    }
                })
                .showBottomSheet(getParentFragmentManager());
    }

    private void checkColor(boolean check , User u, int color){
        if (check){
            u.setColorAge(color);
        }
        else{
            u.setColorZo(color);
        }


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

        changBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int yearC, monthC, dayC;
                if (selectedUser) {

                    Calendar calendar = mUser1.getDate();

                    yearC = calendar.get(Calendar.YEAR);
                    monthC = calendar.get(Calendar.MONTH);
                    dayC = calendar.get(Calendar.DATE);

                } else {
                    Calendar calendar = mUser2.getDate();

                    yearC = calendar.get(Calendar.YEAR);
                    monthC = calendar.get(Calendar.MONTH);
                    dayC = calendar.get(Calendar.DATE);
                }

                // chon ngay
                DatePickerDialog dialogPicker = new DatePickerDialog(mainActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (selectedUser) {
                            Calendar dateSelect = Calendar.getInstance();
                            dateSelect.set(year, month, dayOfMonth, 0, 0, 0);
                            mUser1.setDate(dateSelect);
                            editor.putString("user1", gson.toJson(mUser1));
                            age1 = sendDataFragmentOneActi.getFragmentTime(dateSelect);

                            setUpAge(ageUser1, age1[0]);

                            setUpZodiac(imgSignUser1, singUser1
                                    , zodiacs.get(checkZodiac(month + 1, dayOfMonth)));

                        } else {
                            Calendar dateSelect = Calendar.getInstance();
                            dateSelect.set(year, month, dayOfMonth, 0, 0, 0);
                            mUser2.setDate(dateSelect);
                            editor.putString("user2", gson.toJson(mUser2));
                            age2 = sendDataFragmentOneActi.getFragmentTime(dateSelect);
                            setUpAge(ageUser2, age2[0]);

                            setUpZodiac(imgSignUser2, singUser2
                                    , zodiacs.get(checkZodiac(month + 1, dayOfMonth)));
                        }
                        editor.commit();

                    }
                }, yearC, monthC, dayC);
                dialogPicker.show();
                dialog.dismiss();


            }
        });

        changeColorSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedUser){
                    pickerColor( mainView.findViewById(R.id.age_background_user1), true);
                }
                else{
                    pickerColor( mainView.findViewById(R.id.age_background_user2), true);
                }
                dialog.dismiss();
            }
        });

        changeColorZo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedUser){
                    pickerColor( mainView.findViewById(R.id.zo_background_color1) , false);
                }
                else{
                    pickerColor( mainView.findViewById(R.id.zo_background_color_2), false);
                }
                dialog.dismiss();
            }
        });

        changeSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedUser) {
                    mUser1.setSex(!mUser1.isSex());

                    changSexImg(mUser1, imgSexUser1);
                    editor.putString("user1", gson.toJson(mUser1));
                } else {
                    mUser2.setSex(!mUser2.isSex());

                    changSexImg(mUser2, imgSexUser2);

                    editor.putString("user2", gson.toJson(mUser2));
                }
                dialog.dismiss();

                editor.commit();
            }
        });
    }

    public void changSexImg(User u, ImageView v){
        if (!u.isSex()){
            Picasso.get()
                    .load(R.drawable.male)
                    .into( v);
        }
        else{
            Picasso.get()
                    .load(R.drawable.female)
                    .into(v);
        }
    }

    public void setUpZodiac(ImageView img, TextView txt , Zodiac zodiac ) {
        txt.setText(zodiac.getName());

        Picasso.get()
                .load(zodiac.getImg())
                .into(img);

    }

    public void createDialogInput() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.dialog_input, null, false);
        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


        inputTextDialog = view.findViewById(R.id.editText);
        btnAccept = view.findViewById(R.id.accept);
        btnCancel = view.findViewById(R.id.cancel_dialog_input);
        if (selectedUser) {
            inputTextDialog.setText(mUser1.getName());
        } else {
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
                if (selectedUser) {
                    mUser1.setName(textEdit);
                    editor.putString("user1", gson.toJson(mUser1));
                    name1.setText(textEdit);
                } else {
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
        changeColorZo = view.findViewById(R.id.change_color_background_zo);
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

    public int checkZodiac(int thang, int ngay) {
        if ((ngay >= 21 && thang == 3) || (ngay <= 19 && thang == 4)) {
            return 3;
        } else if ((ngay >= 20 && thang == 4) || (ngay <= 20 && thang == 5)) {
           return 4;
        } else if ((ngay >= 21 && thang == 5) || (ngay <= 21 && thang == 6)) {
            return 5;
        } else if ((ngay >= 22 && thang == 6) || (ngay <= 22 && thang == 7)) {
            return 6;
        } else if ((ngay >= 23 && thang == 7) || (ngay <= 22 && thang == 8)) {
            return 7;
        } else if ((ngay >= 23 && thang == 8) || (ngay <= 22 && thang == 9)) {
            return 8;
        } else if ((ngay >= 23 && thang == 9) || (ngay <= 23 && thang == 10)) {
            return 9;
        } else if ((ngay >= 24 && thang == 10) || (ngay <= 21 && thang == 11)) {
            return 10;
        } else if ((ngay >= 22 && thang == 11) || (ngay <= 21 && thang == 12)) {
           return 11;
        } else if ((ngay >= 22 && thang == 12) || (ngay <= 19 && thang == 1)) {
            return 0;
        } else if ((ngay >= 20 && thang == 1) || (ngay <= 18 && thang == 2)) {
          return 1;
        }
        return 2;
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

    public void addZodiac() {
        zodiacs = new ArrayList<>();
        String[] nameZodiacs = getResources().getStringArray(R.array.zodicac);
        zodiacs.add(new Zodiac(nameZodiacs[0], R.drawable.ma_ket));
        zodiacs.add(new Zodiac(nameZodiacs[1], R.drawable.bao_binh));
        zodiacs.add(new Zodiac(nameZodiacs[2], R.drawable.song_ngu));
        zodiacs.add(new Zodiac(nameZodiacs[3], R.drawable.bach_duong));
        zodiacs.add(new Zodiac(nameZodiacs[4], R.drawable.kim_nguu));
        zodiacs.add(new Zodiac(nameZodiacs[5], R.drawable.song_tu));
        zodiacs.add(new Zodiac(nameZodiacs[6], R.drawable.cu_giai));
        zodiacs.add(new Zodiac(nameZodiacs[7], R.drawable.su_tu));
        zodiacs.add(new Zodiac(nameZodiacs[8], R.drawable.xu_nu));
        zodiacs.add(new Zodiac(nameZodiacs[9], R.drawable.thien_binh));
        zodiacs.add(new Zodiac(nameZodiacs[10], R.drawable.bo_cap));
        zodiacs.add(new Zodiac(nameZodiacs[11], R.drawable.nhan_ma));

    }
}


