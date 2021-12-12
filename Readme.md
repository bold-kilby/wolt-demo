# WoltApplication

This is a standard spring boot application. No special configuration is needed to run it. It can also be checked out on
[Git](https://github.com/bold-kilby/wolt-demo.git).

To help you play with the application, a postman collection is provided under `resources/postman`. It only contains one
request, but you can change that one as you see fit :) 

## Part 2 of the assignment

I don't think the JSON structure is optimal, especially closing times always being on the day on which they occur. I
would instead choose something closer to my model object in `OpeningHours.kt` (obviously :) ).

The reasons for this are as follows:

- Since the opening times are basically intervals, in my opinion it makes more sense to group the endpoints of those
  intervals together. This results in a JSON that is more readable (and thus prevents mistakes when making requests) and
  easier to parse. This is just my preference though.
- More importantly the current JSON structure does not allow for adding more days. For example, suppose you want to add
  the option to also print opening hours for holidays. If someone wants to print `New Year's Eve: 8 PM - 4 AM`, how
  would they do this?

Hence, I would propose a structure like this:

```
{
"monday": [{"open": 3600, "close": 7200}, {"open": 72000, "close": 90000}],
"tuesday": ...,
"wednesday": ...,
"thursday": ...,
"friday": ...,
"saturday": ...,
"sunday": ...,
"additional-days": [
    { "day 1": [{"open": 3600, "close": 7200}] },
    { "day 2": ...}
]
}
```

As you can see, closing times on the next day would be represented by a value greater than 24 hours (90000 seconds
should be 25 hours). Since Monday to Sunday are required, they have a separate field in the TO. Additional days would be
added under the field `additional-days`, which would be interpreted as a map mapping the day's name to its opening
hours.