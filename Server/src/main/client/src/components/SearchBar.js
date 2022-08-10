import React from "react";

export default class SearchBar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      className: this.props.className,
      searchStart: this.props.searchStart,
      searchEnd: this.props.searchEnd,
      placeholder: this.props.placeholder,
      search: this.props.search,
      query: "",
      timer: null,
    };

    this.changeHandler = this.changeHandler.bind(this);
    this.onSearchWrapper = this.onSearchWrapper.bind(this);
  }

  async onSearchWrapper() {
    this.state.searchStart();

    const result = await this.state.search(this.state.query);

    this.state.searchEnd(result);
  }

  changeHandler(e) {
    this.setState({ query: e.target.value });

    clearTimeout(this.state.timer);

    const newTimer = setTimeout(() => {
      this.onSearchWrapper();
    }, 400);

    this.setState({
      timer: newTimer,
    });
  }

  render() {
    return (
      <div>
        <input
          id="search_bar_input"
          type="text"
          className={this.state.className}
          placeholder={this.state.placeholder}
          aria-label={this.state.placeholder}
          onChange={(e) => this.changeHandler(e)}
        />
      </div>
    );
  }
}
