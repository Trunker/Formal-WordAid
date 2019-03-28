package dev.goodjob.wordmemorizationaid;

import android.os.Parcel;
import android.os.Parcelable;

public class Word implements Parcelable {
    public final String WordId;
    public final String WordOriginalForm;
    private final String WordExampleSentence;
    private final String WordExplanation;


    public String getWordId() {
        return WordId;
    }

    public String getWordOriginalForm() {
        return WordOriginalForm;
    }

    public String getWordExampleSentence() {
        return WordExampleSentence;
    }

    public String getWordExplanation() {
        return WordExplanation;
    }

    public Word(String wordId, String wordOriginalForm, String wordExampleSentence, String wordExplanation) {
        WordId = wordId;
        WordOriginalForm = wordOriginalForm;
        WordExampleSentence = wordExampleSentence;
        WordExplanation = wordExplanation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.WordId);
        dest.writeString(this.WordOriginalForm);
        dest.writeString(this.WordExampleSentence);
        dest.writeString(this.WordExplanation);
    }

    protected Word(Parcel in) {
        this.WordId = in.readString();
        this.WordOriginalForm = in.readString();
        this.WordExampleSentence = in.readString();
        this.WordExplanation = in.readString();
    }

    public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel source) {
            return new Word(source);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };
}
