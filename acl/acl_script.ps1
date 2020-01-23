# Ovu skriptu treba pokretati kao Administrator

$group = "SepUsersGroup"
$groupUsers = "Users"
$user = "sep_user"

function Strings-Contains {
 foreach($name in $args[0]) {
	if($name -Match $args[1]) {
		return $true
	}
 }
 
 return $false;
}

$GroupExists = Get-LocalGroup -Name $group
if ($GroupExists -eq $NULL) {
	New-LocalGroup -Name $group
}

# Samo proveravamo da li vec postoji ovaj user 
$userExist =(Get-LocalUser).Name -Contains $user
if(-Not $userExist) {
	$SecureStringPassword = ConvertTo-SecureString $user -AsPlainText -Force
    New-LocalUser -Name $user -Password $SecureStringPassword
	Write-Host "Kreiran novi lokalni User"
}

$userInGroupUsers = Strings-Contains (Get-LocalGroupMember $groupUsers).Name $user
if (-Not $userInGroupUsers) {
	Add-LocalGroupMember -Group $groupUsers -Member $user  # najpre ga dodajemo u Users grupu da bismo mogli da se ulogujemo na ovaj nalog
} 

$userInGroup = Strings-Contains (Get-LocalGroupMember $group).Name $user
if (-Not $userInGroup) {
	Add-LocalGroupMember -Group $group -Member $user
}
#potrebni atributi, na svim mestima su isti
$InheritanceFlags = [System.Security.AccessControl.InheritanceFlags]::None
$PropagationFlag = [System.Security.AccessControl.PropagationFlags]::None
$Allow = [System.Security.AccessControl.AccessControlType]::Allow 
$SpecialPermission = [System.Security.AccessControl.FileSystemRights]::Read
$accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule ($group, $SpecialPermission, $InheritanceFlags,$PropagationFlag, $Allow)


#authentication service 
$path_auth_db = "..\AuthenticationService\src\main\resources\data.sql"
$acl = Get-Acl $path_auth_db
$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_auth_db -AclObject $acl

$path_auth_ks = "..\AuthenticationService\src\main\resources\keystore-auth"
$acl = Get-Acl $path_auth_ks
$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_auth_ks -AclObject $acl

$path_auth_ts = "..\AuthenticationService\src\main\resources\truststore"
$acl = Get-Acl $path_auth_ts
$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_auth_ts -AclObject $acl

#bank service
$path_bank_db = "..\bank_service\src\main\resources\data.sql"
$acl = Get-Acl $path_bank_db
$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_bank_db -AclObject $acl

$path_bank_ks = "..\bank_service\src\main\resources\keystore"
$acl = Get-Acl $path_bank_ks
$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_bank_ks -AclObject $acl

$path_bank_ts = "..\bank_service\src\main\resources\truststore"
$acl = Get-Acl $path_bank_ts
$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_bank_ts -AclObject $acl


#bitcoin deo 
$path_bitcoin_database = "../bitcoin/src/main/resources/data.sql"
$acl = Get-Acl $path_bitcoin_database
$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_bitcoin_database -AclObject $acl

$path_bitcoin_ks = "..\bitcoin\src\main\resources\keystore-btc"
$acl = Get-Acl $path_bitcoin_ks
$acl.AddAccessRule($accessRule)
Set-Acl -Path $path_bitcoin_ks -AclObject $acl


#eureka discovery
$path_eureka_ks = "..\EurekaDiscovery\src\main\resources\keystore-eureka"
$acl = Get-Acl $path_eureka_ks
$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_eureka_ks -AclObject $acl

#paypal
$path_pp_db = "..\PaypalService\src\main\resources\data.sql"
$acl = Get-Acl $path_pp_db
$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_pp_db -AclObject $acl

$path_pp_ks = "..\PaypalService\src\main\resources\keystore-pp"
$acl = Get-Acl $path_pp_ks
$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_pp_ks -AclObject $acl

$path_pp_ts = "..\PaypalService\src\main\resources\truststore"
$acl = Get-Acl $path_pp_ts
$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_pp_ts -AclObject $acl

#zuul
$path_zuul_ts = "..\ZuulGateway\src\main\resources\truststore1"
$acl = Get-Acl $path_zuul_ts
$acl.AddAccessRule($accessRule)  
Set-Acl -Path $path_zuul_ts -AclObject $acl



