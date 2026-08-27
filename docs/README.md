# Pebby User Guide

// Update the title above to match the actual product name

// Product screenshot goes here

// Product intro goes here

## Adding deadlines

Use `deadline` to add a task with a due date. Dates must use the unambiguous
`yyyy-MM-dd` format. Pebby stores the value as a `LocalDate`, so invalid dates
such as `2019-02-30` are rejected.

Example:

`deadline return book /by 2019-12-02`

Pebby displays the date in a more readable format:

```
[D] [ ] return book (by: Dec 02 2019)
```

## Finding deadlines on a date

Use `find yyyy-MM-dd` to show all deadlines occurring on a particular date.

Example: `find 2019-12-02`

## Feature ABC

// Feature details


## Feature XYZ

// Feature details
