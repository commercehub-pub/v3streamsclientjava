package io.dsco.stream.domain;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OrderLineItemActivity
{
    public enum Activity {accept, add, cancel, invoice, reason_update, reject, remove_status, ship}

    private Activity activity;
    private String activityDate; //iso8601
    private Activity formerStatus;
    private Integer quantity;
    private String reason;

    public OrderLineItemActivity() {}

    public OrderLineItemActivity(
            @NotNull Activity activity, @NotNull String activityDate, @Nullable Activity formerStatus, @Nullable Integer quantity, @NotNull String reason)
    {
        this.activity = activity;
        this.activityDate = activityDate;
        this.formerStatus = formerStatus;
        this.quantity = quantity;
        this.reason = reason;
    }

    public Activity getActivity()
    {
        return activity;
    }

    public void setActivity(Activity activity)
    {
        this.activity = activity;
    }

    public String getActivityDate()
    {
        return activityDate;
    }

    public void setActivityDate(String activityDate)
    {
        this.activityDate = activityDate;
    }

    public Activity getFormerStatus()
    {
        return formerStatus;
    }

    public void setFormerStatus(Activity formerStatus)
    {
        this.formerStatus = formerStatus;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }
}
