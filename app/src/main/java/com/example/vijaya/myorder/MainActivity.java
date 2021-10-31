package com.example.vijaya.myorder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements MyRecyclerViewAdapter.ItemClickListener {

    static ArrayList<String> toppings=new ArrayList<>();
    MyRecyclerViewAdapter adapter;
    private static final String MAIN_ACTIVITY_TAG = "MainActivity";
    final int PizzaPrice = 5;
    final int ThinCrustPrice = 1;
    final int NormalCrustPrice = 2;
    final double toppingsPrice=1.5;
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> toppings = new ArrayList<>();
        toppings.add("Pepperoni");
        toppings.add("Mushroom");
        toppings.add("Extra cheese");
        toppings.add("Fresh basil");
        toppings.add("Tomato");
        toppings.add("Fresh garlic");
        toppings.add("Green pepper");
        toppings.add("Black olives");
        toppings.add("Onion");
        toppings.add("Sausage");

        RecyclerView recyclerView = findViewById(R.id.rvAnimals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, toppings);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


        Button button=(Button) findViewById(R.id.submitOrder);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder(v);
            }
        });
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {

        // get user input
        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();

        // check if whipped cream is selected
        CheckBox thrust1 = (CheckBox) findViewById(R.id.whipped_cream_checked);
        boolean wasThinCrust = thrust1.isChecked();

        // check if chocolate is selected
        CheckBox thrust2 = (CheckBox) findViewById(R.id.chocolate_checked);
        boolean wasNoramlCrust = thrust2.isChecked();

        // calculate and store the total price
        double totalPrice = calculatePrice(wasThinCrust, wasNoramlCrust,toppings.size());

        // create and store the order summary
        String orderSummaryMessage = createOrderSummary(userInputName, wasThinCrust, wasNoramlCrust,toppings, totalPrice);

        // Write the relevant code for making the buttons work(i.e implement the implicit and explicit intents
        if(userInputName!=null&&!userInputName.isEmpty()&&userInputName!=""){

            Intent i = new Intent(MainActivity.this, OrderActivity.class);
            i.putExtra("orderSummary",orderSummaryMessage);
            i.putExtra("name",userInputName);
            startActivity(i);
        }else{
            TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
            quantityTextView.setText("Please Enter your Name");
        }

    }



    private String boolToString(boolean bool) {
        return bool ? (getString(R.string.yes)) : (getString(R.string.no));
    }

    private String createOrderSummary(String userInputName, boolean hasWhippedCream, boolean hasChocolate,ArrayList<String> toppings, double price) {
        String orderSummaryMessage = getString(R.string.order_summary_name, userInputName) + "\n" +
                getString(R.string.order_summary_whipped_cream, boolToString(hasWhippedCream)) + "\n" +
                getString(R.string.order_summary_chocolate, boolToString(hasChocolate)) + "\n" +
                "TOPPINGS:"+ toppings.toString()+"\n"+
                getString(R.string.order_summary_quantity, quantity) + "\n" +
                getString(R.string.order_summary_total_price, price) + "\n" +
                getString(R.string.thank_you);


        return orderSummaryMessage;
    }

    /**
     * Method to calculate the total price
     *
     * @return total Price
     */
    private double calculatePrice(boolean hasWhippedCream, boolean hasChocolate, int topping) {
        int basePrice = PizzaPrice;
        if (hasWhippedCream) {
            basePrice += ThinCrustPrice;
        }
        if (hasChocolate) {
            basePrice += NormalCrustPrice;
        }
        return (topping*quantity*toppingsPrice)+(quantity * basePrice);

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method increments the quantity of coffee cups by one
     *
     * @param view on passes the view that we are working with to the method
     */

    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select less than one hundred Pizzas");
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.too_much_coffee);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;
        }
    }

    /**
     * This method decrements the quantity of coffee cups by one
     *
     * @param view passes on the view that we are working with to the method
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select atleast one cup of coffee");
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.too_little_coffee);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;
        }
    }

    public void selectedToppings(ArrayList<String> toppings){

        this.toppings=toppings;
    }


    @Override
    public void onItemClick(View view, int position) {
        
    }
}