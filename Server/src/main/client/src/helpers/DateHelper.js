const isToday = (date) => {
  const today = new Date();

  return (
    date.getDate() == today.getDate() &&
    date.getMonth() == today.getMonth() &&
    date.getFullYear() == today.getFullYear()
  );
};

const isPast = (date) => {
  return date < new Date();
};

export { isToday, isPast };
