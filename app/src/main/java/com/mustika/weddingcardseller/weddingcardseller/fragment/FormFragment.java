package com.mustika.weddingcardseller.weddingcardseller.fragment;


/**
 * A simple {@link Fragment} subclass.
 */
//public class FormFragment extends Fragment {
//
//
//    private EditText mFullNameMEditText, mNickNameMEditText, mFatherFullNameMEditText, mMotherFullNameMEditText,
//            mParentAddressMEditText, mFullNameWEditText, mNickNameWEditText, mFatherFullNameWEditText, mMotherFullNameWEditText,
//            mParentAddressWEditText, mDayAndDateEditText, mTimeEditText, mPlaceEditText, mWeddingCardQuantityEditText,
//    mFullNameOEditText, mNickNameOEditText, mEmailOEditText, mTelephoneOEditText;
//
//    private TextView mFormTextView;
//    String name, description, imageUrl;
//    int price, total;
//    public String fullNameM;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View rootView = inflater.inflate(R.layout.fragment_form, container, false);
//
//        mFullNameMEditText = (EditText) rootView.findViewById(R.id.full_name_m_edit_text);
//        mNickNameMEditText = (EditText) rootView.findViewById(R.id.nick_name_m_edit_text);
//        mFatherFullNameMEditText = (EditText) rootView.findViewById(R.id.father_full_name_m_edit_text);
//        mMotherFullNameMEditText = (EditText) rootView.findViewById(R.id.mother_full_name_m_edit_text);
//        mParentAddressMEditText = (EditText) rootView.findViewById(R.id.parent_address_m_edit_text);
//        mFullNameWEditText = (EditText) rootView.findViewById(R.id.full_name_w_edit_text);
//        mNickNameWEditText = (EditText) rootView.findViewById(R.id.nick_name_w_edit_text);
//        mFatherFullNameWEditText = (EditText) rootView.findViewById(R.id.father_full_name_w_edit_text);
//        mMotherFullNameWEditText = (EditText) rootView.findViewById(R.id.mother_full_name_w_edit_text);
//        mParentAddressWEditText = (EditText) rootView.findViewById(R.id.parent_address_w_edit_text);
//        mDayAndDateEditText = (EditText) rootView.findViewById(R.id.day_and_date_edit_text);
//        mTimeEditText = (EditText) rootView.findViewById(R.id.time_edit_text);
//        mPlaceEditText = (EditText) rootView.findViewById(R.id.place_edit_text);
//        mWeddingCardQuantityEditText = (EditText) rootView.findViewById(R.id.wedding_card_quantity_edit_text);
//        mFullNameOEditText = (EditText) rootView.findViewById(R.id.full_name_o_edit_text);
//        mNickNameOEditText = (EditText) rootView.findViewById(R.id.nick_name_o_edit_text);
//        mEmailOEditText = (EditText) rootView.findViewById(R.id.email_o_edit_text);
//        mTelephoneOEditText = (EditText) rootView.findViewById(R.id.telephone_o_edit_text);
//
////        mFormTextView = (TextView) rootView.findViewById(R.id.form_text_view);
//
//        if (getActivity().getIntent() != null){
//            name = getActivity().getIntent().getExtras().getString(Config.TAG_NAME);
//            description = getActivity().getIntent().getExtras().getString(Config.TAG_DESCRIPTION);
//            price = getActivity().getIntent().getExtras().getInt(Config.TAG_PRICE);
//            imageUrl = getActivity().getIntent().getExtras().getString(Config.TAG_IMAGE_URL);
//
////            mFormTextView.setText(name +"\n"+ description+"\n"+ String.valueOf(price)+"\n"+ imageUrl);
//        }
//
//
//        return rootView;
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        fullNameM = mFullNameMEditText.getText().toString();
//        Order order = new Order();
//        order.setFullNameM(fullNameM);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        fullNameM = mFullNameMEditText.getText().toString();
//        Order order = new Order();
//        order.setFullNameM(fullNameM);
//    }
//}
