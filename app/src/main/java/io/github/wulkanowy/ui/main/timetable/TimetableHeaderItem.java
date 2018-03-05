package io.github.wulkanowy.ui.main.timetable;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractExpandableHeaderItem;
import eu.davidea.viewholders.ExpandableViewHolder;
import io.github.wulkanowy.R;
import io.github.wulkanowy.data.db.dao.entities.Day;

public class TimetableHeaderItem
        extends AbstractExpandableHeaderItem<TimetableHeaderItem.HeaderViewHolder, TimetableSubItem> {

    private Day day;

    TimetableHeaderItem(Day day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TimetableHeaderItem that = (TimetableHeaderItem) o;

        return new EqualsBuilder()
                .append(day, that.day)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(day)
                .toHashCode();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.timetable_header;
    }

    @Override
    public HeaderViewHolder createViewHolder(View view, FlexibleAdapter adapter) {
        return new HeaderViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, HeaderViewHolder holder, int position, List payloads) {
        holder.onBind(day, getSubItems());
    }

    static class HeaderViewHolder extends ExpandableViewHolder {

        @BindView(R.id.timetable_header_day)
        TextView dayName;

        @BindView(R.id.timetable_header_date)
        TextView date;

        @BindView(R.id.timetable_header_alert_image)
        ImageView alert;

        @BindView(R.id.timetable_header_free_name)
        TextView freeName;

        @BindColor(R.color.secondary_text)
        int secondaryColor;

        @BindColor(R.color.free_day)
        int backgroundFreeDay;

        HeaderViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            view.setOnClickListener(this);
            ButterKnife.bind(this, view);
        }

        void onBind(Day item, List<TimetableSubItem> subItems) {
            dayName.setText(StringUtils.capitalize(item.getDayName()));
            date.setText(item.getDate());
            alert.setVisibility(isSubItemNewMovedInOrChanged(subItems) ? View.VISIBLE : View.INVISIBLE);
            freeName.setVisibility(item.isFreeDay() ? View.VISIBLE : View.INVISIBLE);
            freeName.setText(item.getFreeDayName());

            if (item.isFreeDay()) {
                ((FrameLayout) getContentView()).setForeground(null);
                getContentView().setBackgroundColor(backgroundFreeDay);
                dayName.setTextColor(secondaryColor);
            }
        }

        private boolean isSubItemNewMovedInOrChanged(List<TimetableSubItem> subItems) {
            boolean isAlertActive = false;

            for (TimetableSubItem subItem : subItems) {
                if (subItem.getLesson().getIsMovedOrCanceled() ||
                        subItem.getLesson().getIsNewMovedInOrChanged()) {
                    isAlertActive = true;
                }
            }
            return isAlertActive;
        }
    }
}
