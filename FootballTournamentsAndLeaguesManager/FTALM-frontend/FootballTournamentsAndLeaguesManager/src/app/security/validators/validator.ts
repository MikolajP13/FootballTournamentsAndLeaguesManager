export class Validator {
  private static emailRegex: RegExp = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/;

  //minimum eight chars, maximum twenty, alphanumeric characters, underscore and dot
  private static usernameRegex: RegExp =
    /^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$/;

  private static firstAndLastNameRegex: RegExp = /^[a-z ,.'-]+$/i;

  private static competitionNameRegex: RegExp = /^(\w+\s)*\w+$/;

  //minimum eight chars, at least: one number, one uppercase, one lowercase letter and one special character
  private static passwordRegex: RegExp =
    /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^0-9a-zA-Z]).{8,}$/;

  //weak password means that it'll take to crack it up to 1 day
  private static isWeakPassword1: RegExp = /^[0-9]{1,15}$/;
  private static isWeakPassword2: RegExp = /^[a-z]{1,10}$/;
  private static isWeakPassword3: RegExp =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{1,9}$/;
  private static isWeakPassword4: RegExp =
    /^(?=(?:.*\d))(?=(?:.*[a-z]))(?=(?:.*[A-Z]))(?=(?:.*[!@#\$%\^\&*\)\(+=._-]))[a-zA-Z0-9!@#\$%\^\&*\)\(+=._-]{1,9}$/;

  //medium strength password means that it'll take to crack it up to 1 year
  private static isMediumPassword1: RegExp = /^[0-9]{16,}$/;
  private static isMediumPassword2: RegExp = /^[a-z]{11,13}$/;
  private static isMediumPassword3: RegExp =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{10}$/;
  private static isMediumPassword4: RegExp =
    /^(?=(?:.*\d))(?=(?:.*[a-z]))(?=(?:.*[A-Z]))(?=(?:.*[!@#\$%\^\&*\)\(+=._-]))[a-zA-Z0-9!@#\$%\^\&*\)\(+=._-]{10}$/;

  //strong password means that it'll take to crack it more than 1 year
  private static isStrongPassword1: RegExp = /^[a-z]{14,}$/;
  private static isStrongPassword2: RegExp =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{11,}$/;
  private static isStrongPassword3: RegExp =
    /^(?=(?:.*\d))(?=(?:.*[a-z]))(?=(?:.*[A-Z]))(?=(?:.*[!@#\$%\^\&*\)\(+=._-]))[a-zA-Z0-9!@#\$%\^\&*\)\(+=._-]{11,}$/;

  static isEmailValid(email: string): boolean {
    //TODO: add database validation
    return Validator.emailRegex.test(email);
  }

  static isUsernameValid(username: string): boolean {
    //TODO: add database validation
    return Validator.usernameRegex.test(username);
  }

  static isFirstOrLastNameValid(name: string): boolean {
    return Validator.firstAndLastNameRegex.test(name);
  }

  static isPasswordValid(password: string): boolean {
    return Validator.passwordRegex.test(password);
  }

  // player must have at least 7 years old
  static isDateOfBirthValid(date: Date): boolean {
    const currentDate = new Date();
    return date <= new Date(currentDate.getFullYear() - 7, currentDate.getMonth(), currentDate.getDate());
  }

  static isHeightValid(height: string): boolean {
    return parseInt(height) > 0 && parseInt(height) < 230;
  }

  static isCompetitionNameValid(competitionName: string): boolean {
    return Validator.competitionNameRegex.test(competitionName);
  }

  static isTournamentNumberOfTeamsValid(numberOfTeams: string): boolean {
    return parseInt(numberOfTeams) >= 4 && parseInt(numberOfTeams) <= 32;
  }

  static isLeagueNumberOfTeamsValid(numberOfTeams: string): boolean {
    return parseInt(numberOfTeams) >= 6 && parseInt(numberOfTeams) <= 18;
  }

  static isCompetitionStartDateValid(date: Date): boolean {
    const currentDate = new Date();
    return date >= new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate());
  }

  static isWeakPassword(password: string): boolean {
    return (
      Validator.isWeakPassword1.test(password) ||
      Validator.isWeakPassword2.test(password) ||
      Validator.isWeakPassword3.test(password) ||
      Validator.isWeakPassword4.test(password)
    );
  }

  static isMediumStrengthPassword(password: string): boolean {
    return (
      Validator.isMediumPassword1.test(password) ||
      Validator.isMediumPassword2.test(password) ||
      Validator.isMediumPassword3.test(password) ||
      Validator.isMediumPassword4.test(password)
    );
  }

  static isStrongPassword(password: string): boolean {
    return (
      Validator.isStrongPassword1.test(password) ||
      Validator.isStrongPassword2.test(password) ||
      Validator.isStrongPassword3.test(password)
    );
  }

  /*high entropy indicates a high amount of variation in the selected password, 
    longer password with a large variation has higher entropy, making it harder to crack
    E = L * log2(R)
    0-24 -> Poor password / 25-49 -> Weak password
    50-74 -> Medium strength password / 75-100 -> Strong password
  */
  static calculateEntropy(password: string): number {
    const length = password.length;
    const characterSetSize = this.countCharacterSetSize(password);
    const entropy = length * Math.log2(characterSetSize);
    return entropy;
  }
  
  static countCharacterSetSize(password: string): number {
    const lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
    const uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    const numbers = "0123456789";
    const specialCharacters = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    let characterSet = "";
  
    if (password.match(/[a-z]/)) characterSet += lowercaseLetters;
    if (password.match(/[A-Z]/)) characterSet += uppercaseLetters;
    if (password.match(/\d/)) characterSet += numbers;
    if (password.match(/[!@#$%^&*()_+\-=\[\]{}|;:',.<>?]/)) characterSet += specialCharacters;
  
    return characterSet.length;
  }

  static countOccurrencesInPassword(password: string): number {
    return new Set(password.toLowerCase().split("")).size;
  } 
}
