import UserTypes from "./UserTypes";

export default class AccessLevelService {
  static currentUser = {
    id: "",
    username: "",
    email: "",
    name: "",
    surname: "",
    accessLevel: -1,
    projectIds: [],
    hasFetched: false,
  };

  static async getAccessLevel() {
    const response = await fetch("/api/auth/accesslevel");

    const data = await response.json();

    let accessLevel = -1;
    if (response.status === 200) {
      accessLevel = data.accessLevel;
      this.currentUser.accessLevel = accessLevel;
    }

    return accessLevel;
  }

  static async fetchUser() {
    const response = await fetch("/api/auth/user");

    const data = await response.json();

    if (response.status === 200) {
      this.currentUser.id = data.id;
      this.currentUser.username = data.username;
      this.currentUser.email = data.email;
      this.currentUser.name = data.name;
      this.currentUser.surname = data.surname;
      this.currentUser.type = data.type;
      this.currentUser.projectIds = data.projectIds;
      this.currentUser.hasFetched = true;
    }

    return this.currentUser;
  }

  static async getUser() {
    if (this.currentUser.hasFetched) {
      return this.currentUser;
    } else {
      return await this.fetchUser();
    }
  }

  static isHigherOrEqual(userAccessLevel, referenceAccessLevel) {
    return userAccessLevel >= referenceAccessLevel;
  }
}
