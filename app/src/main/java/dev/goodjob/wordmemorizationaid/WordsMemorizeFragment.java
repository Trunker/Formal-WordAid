package dev.goodjob.wordmemorizationaid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WordsMemorizeFragment extends Fragment {

    private Word myWord;
    private static final String PRODUCT_KEY = "product_key";

    public static WordsMemorizeFragment newInstance(Word word) {
        Bundle args = new Bundle();
        args.putParcelable(PRODUCT_KEY, word);
        WordsMemorizeFragment fragment = new WordsMemorizeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TextView tvWordOF;
    public TextView tvWordES;
    public TextView tvWOrdExp;

    public WordsMemorizeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_words_memorize, container, false);
        Bundle args = getArguments();
        if(args == null){
            throw new AssertionError();
        }

        myWord = args.getParcelable(PRODUCT_KEY);

        if(myWord == null){
            throw new AssertionError();
        }

        tvWordOF = rootView.findViewById(R.id.tvWMFWordOF);
        tvWordES = rootView.findViewById(R.id.tvWMFWordExamS);
        tvWOrdExp = rootView.findViewById(R.id.tvWMFExp);


        tvWordOF.setText(myWord.getWordOriginalForm());
        tvWordES.setText(myWord.getWordExampleSentence());
        tvWOrdExp.setText(myWord.getWordExplanation());
        return rootView;
    }

}
