import UserTypes from "./UserTypes";
import userTypeStringToOrdinal from "./UserTypesConverter";

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
    hasFetchedAccessLevel: false,
  };

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
      this.currentUser.accessLevel = userTypeStringToOrdinal(data.type);
      this.currentUser.hasFetched = true;
    }
    return this.currentUser;
  }

  static async getAccessLevel() {
    if (this.hasFetchedAccessLevel) {
      return this.currentUser.accessLevel;
    }

    const response = await fetch("/api/auth/accesslevel");

    const data = await response.json();

    let accessLevel = -1;
    if (response.status === 200) {
      accessLevel = data.accessLevel;
      this.currentUser.accessLevel = accessLevel;
      this.currentUser.hasFetchedAccessLevel = true;
    }

    return data.accessLevel;
  }

  static async getUser() {
    if (this.currentUser.hasFetched) {
      return this.currentUser;
    } else {
      return await this.fetchUser();
    }
  }

  static async getUsername() {
    if (this.currentUser.hasFetched) {
      return this.currentUser.username;
    } else {
      const user = await this.fetchUser();
      return this.currentUser.username;
    }
  }

  static async getTeamMembers(projectID) {
    const response = await fetch(`/api/projects/${projectID}/users`);

    if (response.status === 200) {
      const data = await response.json();

      return this.mergeTeamMemberResponse(data);
    }
    return [];
  }

  static mergeTeamMemberResponse(data) {
    if (data.teamMembers && data.projectTeamMembers) {
      const teamMembers = data.teamMembers;
      const projectTeamMembers = data.projectTeamMembers;

      const teamMembersLength = teamMembers.length;
      const projectTeamMembersLength = projectTeamMembers.length;

      let result = [];

      if (teamMembersLength === projectTeamMembersLength) {
        for (var i = 0; i < teamMembersLength; i++) {
          for (var j = 0; j < projectTeamMembersLength; j++) {
            const teamMember = teamMembers[i];
            const projectMember = projectTeamMembers[j];
            if (teamMember.id === projectMember.id) {
              const found = projectMember;
              found.currentProjectRole = teamMember.projectRole;

              result.push(found);
            }
          }
        }

        return result;
      }
    }
    return [];
  }

  static getProjectRoleIfCurrentUserIsMember(currentUser, projectTeamMembers) {
    const found = projectTeamMembers.find((m) => m.id === currentUser.id);

    if (found) {
      const ordinalProjectRole = userTypeStringToOrdinal(
        found.currentProjectRole
      );

      return {
        isMember: true,
        projectTeamMember: found,
        ordinal: ordinalProjectRole,
      };
    } else {
      return {
        isMember: false,
        projectTeamMember: null,
      };
    }
  }

  static isHigherOrEqual(userAccessLevel, referenceAccessLevel) {
    return userAccessLevel >= referenceAccessLevel;
  }
}
