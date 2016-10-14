Psuedo-Code k-match:	
```
procedure k-match(path[] instancep,path[] 	templatep,int tocheckT, path checked):
	path[][] solutions:=[][]
	int k:=ALGO_SPACE
	len_templatep=length of templatep
	len_instancep=length of instancep
	if (tocheckT <= len_templatep) then
		for (i=1;i<=len_instancep;i++):
			if (instancep[i] is_not_in 	checked ) then
				c:=compatibility(tempaltep	[tocheckT],instancep[i])
				if c == True then		
					if k <> 0 then
						checked_app=checke	d
						checked_app.append	(instancep[i])
						k-match(instancep,	templatep,	tocheckT+1,	checked_app)
						k=k-1
					else
						break;
					endif
				endif
			endif
		endfor

    else
    	solutions.append(checked)
    endif
    return solutions
end_procedure
```