package com.ats.NIEN.abongda.model;

public class UserModel {
	private long Account;
	private String Address;
	private String Avatar;
	private String BetResult;
	private int ClientId;
	private int DifferenceMatchScore;
	private String Email;
	private String Fullname;
	private int Id;
	private int IsEmailVerified;
	private int IsPhoneVerified;
	private int LostMatchScore;
	private String Name;
	private String Password;
	private String Phone;
	private int Rank;
	private String RegisterDateTime;
	private int Status;
	private int TotalCoinScore;
	private int TotalMatchScore;
	private long WonCoinScore;
	private int WonMatchScore;
	public long getAccount() {
		return Account;
	}
	public void setAccount(long account) {
		Account = account;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getAvatar() {
		return Avatar;
	}
	public void setAvatar(String avatar) {
		Avatar = avatar;
	}
	public String getBetResult() {
		return BetResult;
	}
	public void setBetResult(String betResult) {
		BetResult = betResult;
	}
	public int getClientId() {
		return ClientId;
	}
	public void setClientId(int clientId) {
		ClientId = clientId;
	}
	public int getDifferenceMatchScore() {
		return DifferenceMatchScore;
	}
	public void setDifferenceMatchScore(int differenceMatchScore) {
		DifferenceMatchScore = differenceMatchScore;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getFullname() {
		return Fullname;
	}
	public void setFullname(String fullname) {
		Fullname = fullname;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getIsEmailVerified() {
		return IsEmailVerified;
	}
	public void setIsEmailVerified(int isEmailVerified) {
		IsEmailVerified = isEmailVerified;
	}
	public int getIsPhoneVerified() {
		return IsPhoneVerified;
	}
	public void setIsPhoneVerified(int isPhoneVerified) {
		IsPhoneVerified = isPhoneVerified;
	}
	public int getLostMatchScore() {
		return LostMatchScore;
	}
	public void setLostMatchScore(int lostMatchScore) {
		LostMatchScore = lostMatchScore;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public int getRank() {
		return Rank;
	}
	public void setRank(int rank) {
		Rank = rank;
	}
	public String getRegisterDateTime() {
		return RegisterDateTime;
	}
	public void setRegisterDateTime(String registerDateTime) {
		RegisterDateTime = registerDateTime;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public int getTotalCoinScore() {
		return TotalCoinScore;
	}
	public void setTotalCoinScore(int totalCoinScore) {
		TotalCoinScore = totalCoinScore;
	}
	public int getTotalMatchScore() {
		return TotalMatchScore;
	}
	public void setTotalMatchScore(int totalMatchScore) {
		TotalMatchScore = totalMatchScore;
	}
	public long getWonCoinScore() {
		return WonCoinScore;
	}
	public void setWonCoinScore(long wonCoinScore) {
		WonCoinScore = wonCoinScore;
	}
	public int getWonMatchScore() {
		return WonMatchScore;
	}
	public void setWonMatchScore(int wonMatchScore) {
		WonMatchScore = wonMatchScore;
	}
	public UserModel(long account, String address, String avatar,
			String betResult, int clientId, int differenceMatchScore,
			String email, String fullname, int id, int isEmailVerified,
			int isPhoneVerified, int lostMatchScore, String name,
			String password, String phone, int rank, String registerDateTime,
			int status, int totalCoinScore, int totalMatchScore,
			long wonCoinScore, int wonMatchScore) {
		super();
		Account = account;
		Address = address;
		Avatar = avatar;
		BetResult = betResult;
		ClientId = clientId;
		DifferenceMatchScore = differenceMatchScore;
		Email = email;
		Fullname = fullname;
		Id = id;
		IsEmailVerified = isEmailVerified;
		IsPhoneVerified = isPhoneVerified;
		LostMatchScore = lostMatchScore;
		Name = name;
		Password = password;
		Phone = phone;
		Rank = rank;
		RegisterDateTime = registerDateTime;
		Status = status;
		TotalCoinScore = totalCoinScore;
		TotalMatchScore = totalMatchScore;
		WonCoinScore = wonCoinScore;
		WonMatchScore = wonMatchScore;
	}
	public UserModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserModel(long account, String name, int rank) {
		super();
		Account = account;
		Name = name;
		Rank = rank;
	}
	public UserModel(int differenceMatchScore, int lostMatchScore, String name,
			int wonMatchScore, int rank) {
		super();
		DifferenceMatchScore = differenceMatchScore;
		LostMatchScore = lostMatchScore;
		Name = name;
		WonMatchScore = wonMatchScore;
		Rank = rank;
	}
	public UserModel(int id) {
		super();
		Id = id;
	}
	public UserModel(int totalCoinScore, int totalMatchScore,
			long wonCoinScore, int wonMatchScore) {
		super();
		TotalCoinScore = totalCoinScore;
		TotalMatchScore = totalMatchScore;
		WonCoinScore = wonCoinScore;
		WonMatchScore = wonMatchScore;
	}
	
	
	
}
