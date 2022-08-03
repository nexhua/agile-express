export default function DateRow(props) {
  const dateString = new Date(props.date).toDateString();

  return <p className="my-2">{dateString}</p>;
}
