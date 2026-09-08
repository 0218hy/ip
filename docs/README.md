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

## Viewing a schedule for a date

Use `schedule yyyy-MM-dd` to show deadlines due on a date and events that occur on that date.
Events lasting multiple days appear on every date from their start through their end. Incomplete
tasks are shown before completed tasks.

Example: `schedule 2019-12-02`

## Feature ABC

// Feature details


## Feature XYZ

// Feature details
