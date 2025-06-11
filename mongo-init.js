// Sélectionner la base de données
db = db.getSiblingDB('micro_service');

// Patients
db.patients.insertMany([
  {
    _id: "1",
    first_name: "Jean",
    last_name: "Borderline",
    birth_date: "10/05/1970", // 53 ans - pour Borderline
    gender: "H",
    address: "123 Rue Paris",
    phone: "+33-123-456"
  },
  {
    _id: "2",
    first_name: "Marie",
    last_name: "Danger",
    birth_date: "22/07/1995", // 28 ans - pour In Danger
    gender: "F",
    address: "456 Rue Lyon",
    phone: "+33-234-567"
  },
  {
    _id: "3",
    first_name: "Paul",
    last_name: "Early",
    birth_date: "05/12/2000", // 23 ans - pour Early Onset
    gender: "H",
    address: "789 Rue Marseille",
    phone: "+33-345-678"
  }
]);

// Histories
db.history.insertMany([
  {
    _id: "1",
    id_patient: "1",
    name_patient: "Jean Borderline",
    note: "Hémoglobine A1C, Microalbumine, Taille, Poids", // 4 triggers - Borderline
     riskLevel: "" 
  },
  {
    _id: "2",
    id_patient: "2",
    name_patient: "Marie Danger",
    note: "Hémoglobine A1C, Microalbumine, Taille, Poids, Fumeuse, Cholestérol", // 6 triggers - In Danger
     riskLevel: "" 
  },
  {
    _id: "3",
    id_patient: "3",
    name_patient: "Paul Early",
    note: "Hémoglobine A1C, Microalbumine, Taille, Poids, Fumeur, Cholestérol, Vertiges, Rechute", // 8 triggers - Early Onset
     riskLevel: "" 
  },
  {
    _id: "4",
    id_patient: "3",
    name_patient: "Paul Early",
    note: "Hémoglobine A1C, Microalbumine, Taille, Poids, Fumeuse, Cholestérol", // 6 triggers - In Danger
    riskLevel: "" 
  },
  {
    _id: "5",
    id_patient: "3",
    name_patient: "Paul Early",
    note: "Hémoglobine A1C, Microalbumine, Taille, Poids, Fumeur, Cholestérol, Vertiges, Rechute",// 8 triggers - Early Onset
    riskLevel: "" 
  }
]);

// Diabetes Reports
db.diabetes_reports.insertMany([
  {
    _id: "1",
    risk: "Borderline"
  },
  {
    _id: "2",
    risk: "In Danger"
  },
  {
    _id: "3",
    risk: "Early Onset"
  }
]);

// User Accounts
db.user_accounts.insertMany([
  {
    _id: ObjectId("666111111111111111111111"),
    username: "admin",
    password: "$2a$10$7FqHjvmjLpsNkG/AeRkLkOcIUzEW9bVdrPxHJqgP9hbf8xH.vyENq", // "admin" encodé en BCrypt
    roles: ["admin"]
  }
])