We propose two heuristics balancing efficiency and quality in terms of precision and recall.

Heuristic 1: k-matching. Evidence collection model verification logically traverses the permutation tree of the flows φj∈Φ(m) of I with a breadth-first search, and selects a proper sub-tree according to k and flows φi∈Φ(m) of T . First, a node j at depth d=1,...,β in the permutation tree, with β the cardinality of Φ(m), is traversed iff its parent has less than k selected children in the sub-tree; then, it is selected iff φj ∈Φ(m ) matches the corresponding φd∈Φ(m) according to Definition 5.1. The resulting sub-tree includes zero or more isomorphisms between m and m, represented as paths of length β. In the worst case scenario, the algorithm has an exponential asymptotic behavior O(kβ−k+1·(k−1)!), which for k=n degenerates to the exhaustive algorithm O(β!). We note that, for small k, the complexity is far lower than the one of exhaustive algorithm.


Psuedo-Code k-match:	
```
//ALGO_SPACE is a costant corresponding to k, which is the number of possible solutions to manage.
//instancep is an array containing the CM instance flows 
//templatep is an array containing the CM Template flows 

//tocheckT is the index of the CM Tempalte flow under comparison. k-match heuristic uses as pivot array tamplatep, indeed it checks once every CM Tempate flows against all CM Instance flows.
//checked is the array of CM instance flows already mapped in CM Template flows.

//heuristic starts with k-match(instancep,tempaltep,0,[])

procedure k-match(path[] instancep,path[] 	templatep,int tocheckT, path[] checked):
	path[][] solutions:=[][]
	int k:=ALGO_SPACE
	len_templatep=length of templatep
	len_instancep=length of instancep
	if (tocheckT <= len_templatep) then
		for (i=1;i<=len_instancep;i++):
			if (instancep[i] is_not_in 	checked ) then
				c:=compatibility(tempaltep[tocheckT],instancep[i])
				if c == True then		
					if k <> 0 then
						checked_app=checked
						checked_app.append(instancep[i])
						k-match(instancep,templatep,tocheckT+1,checked_app)
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

Heuristic 2: Ordered k-matching. Evidence collection model verification is carried out by first ordering the flows in Φ(m) and Φ(m), and then applying k-matching heuristic. We use an ordering function that recursively compares nodes at the same depth d, with d=1,...,β, from the ancestors to the leafs. For each d, only flows that have not been ordered yet according to the previous runs of the ordering function are considered. The ordering function is based on the hierarchy of mechanisms HM and given two flows φi and φj, with i>j, φi is ordered first iff mechanism θi at depth d of φi and mechanism θj at depth d of φj are such that θj≺θi. In the worst case scenario, the algorithm has the same asymptotic behavior as Heuristic 1, since the complexity of the ordering process is negligible compared to the one of k-matching.

```
//it uses the same function k-match, but instancep and templatep are ordered  based on the hierarchy of mechanisms HM
// ordered(path[] paths) is the ordering function, returns an ordered array

solutions=k-match(ordered(instancep),ordered(templatep),0,[])
```
