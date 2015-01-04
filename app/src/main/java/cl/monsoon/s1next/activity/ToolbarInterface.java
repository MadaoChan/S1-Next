package cl.monsoon.s1next.activity;

import java.util.List;

public interface ToolbarInterface {

    public static interface OnDropDownItemSelectedListener {

        public void onToolbarDropDownItemSelected(int position);
    }

    public static interface SpinnerInteractionCallback {

        /**
         * Set up ToolBar's drop down items.
         */
        public void setupToolbarDropDown(List<String> dropDownItem);
    }
}
