export default function DateRow(props) {
  const dateString = new Date(props.date).toDateString();

  let classNames = ["my-2"];
  if (props.className) {
    classNames = classNames.concat(props.className);
  }

  return (
    <p className={classNames.join(" ")} onClick={props.onClick}>
      {dateString}
    </p>
  );
}
