package org.robobinding.itempresentationmodel;

/**
 * Created by dhu on 15/8/26.
 */
public interface ItemViewFactory {
    int getItemViewTypeCount();

    int getItemViewType(int position, Object item);

    int getItemLayoutId(int position, Object item);

    public static final class Default implements ItemViewFactory {
        public static final Default INSTANCE = new Default();

        @Override
        public int getItemViewTypeCount() {
            return 1;
        }

        @Override
        public int getItemViewType(int position, Object item) {
            return 0;
        }

        @Override
        public int getItemLayoutId(int position, Object item) {
            return 0;
        }

    }
}
