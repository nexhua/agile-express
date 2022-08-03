import { React, useState } from "react";
import DatePicker from "react-datepicker";

export default function AppDatePicker(props) {
  const [startDate, setStartDate] = useState(new Date());
  return (
    <DatePicker
      id={props.id}
      allowSameDay={props.allowSameDay}
      selected={startDate}
      onChange={(date) => setStartDate(date)}
      showTimeSelect
      dateFormat="Pp"
      className="w-100"
    />
  );
}
