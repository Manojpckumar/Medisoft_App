package com.example.medisoft.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medisoft.Authentication.LoginActivity;
import com.example.medisoft.R;
import com.example.medisoft.Utils.AppPreference;
import com.example.medisoft.Utils.AppUtils;
import com.example.medisoft.Utils.MyApplication;
import com.example.medisoft.databinding.FragmentPOrderBinding;
import com.example.medisoft.databinding.FragmentProfileBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        binding.ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppPreference.getInstance(MyApplication.mContext).setString(AppPreference.USERNAME, "0");
                AppPreference.getInstance(MyApplication.mContext).setString(AppPreference.IS_ADMIN, "0");
                AppPreference.getInstance(MyApplication.mContext).setString(AppPreference.CLIENTID, "0");
                AppPreference.getInstance(MyApplication.mContext).setString(AppPreference.CLIENTNAME, "0");
                getContext().startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
                AppUtils.showToast(getContext(), "Loggged out successfully");

            }
        });

        return binding.getRoot();
    }
}