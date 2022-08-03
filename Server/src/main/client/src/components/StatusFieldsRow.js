export default function StatusFieldsRow(props) {
  const statusFields = props.statusFields.map((field, index) => {
    return (
      <span
        key={field + index}
        className="badge app-bg-primary border border-2 border-secondary fw-lighter"
      >
        {field}
      </span>
    );
  });

  return (
    <div className="my-2 w-100 d-flex flex-nowrap gap-2 overflow-hidden">
      {statusFields}
    </div>
  );
}
