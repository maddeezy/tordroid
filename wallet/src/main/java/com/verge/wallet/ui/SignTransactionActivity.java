package com.vergepay.wallet.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.vergepay.wallet.Constants;
import com.vergepay.wallet.ExchangeHistoryProvider.ExchangeEntry;
import com.vergepay.wallet.R;

import javax.annotation.Nullable;

public class SignTransactionActivity extends AbstractWalletFragmentActivity
        implements MakeTransactionFragment.Listener, TradeStatusFragment.Listener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_wrapper);

        if (savedInstanceState == null) {
            Fragment fragment = MakeTransactionFragment.newInstance(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    public void onSignResult(final @Nullable Exception error, final @Nullable ExchangeEntry exchange) {
        final Intent result = new Intent();
        result.putExtra(Constants.ARG_ERROR, error);
        result.putExtra(Constants.ARG_EXCHANGE_ENTRY, exchange);
        setResult(RESULT_OK, result);

        if (error != null || exchange == null) {
            finish();
        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.replace(R.id.container, TradeStatusFragment.newInstance(exchange, true));
            transaction.commit();
        }
    }

    @Override
    public void onFinish() {
        finish();
    }
}
