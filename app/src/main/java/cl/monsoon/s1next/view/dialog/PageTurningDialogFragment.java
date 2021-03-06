package cl.monsoon.s1next.view.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.SeekBar;

import cl.monsoon.s1next.R;
import cl.monsoon.s1next.databinding.DialogPageTurningBinding;
import cl.monsoon.s1next.viewmodel.PageTurningViewModel;

/**
 * A dialog shows {@link SeekBar} and {@link EditText} to
 * display the page number you want to go to.
 * <p>
 * Host class should implement {@link PageTurningDialogFragment.OnPageTurnedListener}
 * in order to to handle the page turning event.
 */
public final class PageTurningDialogFragment extends DialogFragment {

    public static final String TAG = PageTurningDialogFragment.class.getName();

    private static final String ARG_TOTAL_PAGES = "total_pages";
    private static final String ARG_CURRENT_PAGE = "current_page";

    /**
     * The serialization (saved instance state) Bundle key representing
     * the SeekBar's progress.
     */
    private static final String STATE_SEEK_BAR_PROGRESS = "seek_bar_progress";

    private PageTurningViewModel mPageTurningViewModel;

    public PageTurningDialogFragment() {
        // Every fragment must have an empty constructor, so it
        // can be instantiated when restoring its activity's state.
    }

    @SuppressLint("ValidFragment")
    public PageTurningDialogFragment(int totalPages, int currentPage) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_TOTAL_PAGES, totalPages);
        bundle.putInt(ARG_CURRENT_PAGE, currentPage);
        setArguments(bundle);
    }

    @NonNull
    @Override
    public final Dialog onCreateDialog(Bundle savedInstanceState) {
        DialogPageTurningBinding binding = DataBindingUtil.inflate(getActivity().getLayoutInflater(),
                R.layout.dialog_page_turning, null, false);

        int seekBarProgress;
        if (savedInstanceState == null) {
            seekBarProgress = getArguments().getInt(ARG_CURRENT_PAGE);
        } else {
            seekBarProgress = savedInstanceState.getInt(STATE_SEEK_BAR_PROGRESS);
        }

        // SeekBar max is zero-based
        mPageTurningViewModel = new PageTurningViewModel(getArguments().getInt(ARG_TOTAL_PAGES) - 1,
                seekBarProgress);
        binding.setPageTurningViewModel(mPageTurningViewModel);

        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.dialog_title_page_turning)
                .setView(binding.getRoot())
                .setPositiveButton(getText(R.string.dialog_button_text_go),
                        (dialog, which) -> {
                            if (!TextUtils.isEmpty(binding.value.getText())) {
                                ((OnPageTurnedListener) getParentFragment()).onPageTurned(
                                        mPageTurningViewModel.getSeekBarProgress());
                            }
                        })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    @Override
    @CallSuper
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(STATE_SEEK_BAR_PROGRESS, mPageTurningViewModel.getSeekBarProgress());
    }

    /**
     * Callback interface for responding to page turning.
     */
    public interface OnPageTurnedListener {

        /**
         * This method will be invoked when a page is selected.
         *
         * @param position Position index of the new selected page.
         */
        void onPageTurned(int position);
    }
}
